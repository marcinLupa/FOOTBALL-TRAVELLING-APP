package service;

import dto.*;
import exceptions.MyException;
import impl.AirlineRepositoryImpl;
import impl.CityRepositoryImpl;
import impl.FlightRepositoryImpl;
import lombok.RequiredArgsConstructor;
import repositories.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor

public class AddingToDatebaseService {

    private final FlightRepository flightRepository;
    private final AirlineRepository airlineRepository;
    private final CityRepository cityRepository;
    private final GameRepository gameRepository;
    private final MatchRepository matchRepository;
    private final LeagueRepository leagueRepository;
    private final SearchRepository searchRepository;
    private final TeamRepository teamRepository;


    private final ActualWeatherRepository actualWeatherRepository;

    public FlightDto addFlightWithCitiesAndAirline(FlightDto flightDto) {
        if (flightDto == null) {
            return null;
        }
        String airlineName = flightDto.getAirline().getCarrierName();

        AirlineDto airlineDto = airlineRepository
                .findByName(airlineName)
                .orElseGet(() -> airlineRepository
                        .addOrUpdate(AirlineDto.builder()
                                .AirlineId(flightDto.getAirline().getAirlineId())
                                .carrierName(airlineName)
                                .build())
                        .orElseThrow(() -> new MyException("ADD AIRLINE EXCEPTION")));

        String arrivalCityName = flightDto.getArrivalCity().getCityName();
        CityDto arrivalCity = cityRepository
                .findByName(arrivalCityName)
                .orElseGet(() -> cityRepository
                        .addOrUpdate(CityDto.builder()
                                .cityName(arrivalCityName)
                                .airportName(flightDto.getArrivalCity().getAirportName())
                                .airportSkyScannerid(flightDto.getArrivalCity().getAirportSkyScannerid())
                                .cityId(flightDto.getArrivalCity().getCityId())
                                .build())
                        .orElseThrow(() -> new MyException("ADD AIRLINE EXCEPTION")));

        String departureCityName = flightDto.getDepartureCity().getCityName();

        CityDto departureCity = cityRepository
                .findByName(departureCityName)
                .orElseGet(() -> cityRepository
                        .addOrUpdate(CityDto.builder()
                                .cityName(departureCityName)
                                .airportName(flightDto.getDepartureCity().getAirportName())
                                .airportSkyScannerid(flightDto.getDepartureCity().getAirportSkyScannerid())
                                .cityId(flightDto.getDepartureCity().getCityId())
                                .build())
                        .orElseThrow(() -> new MyException("ADD AIRLINE EXCEPTION")));

        //   AirlineDto airlineDto=flightDto.getAirline();
//        CityDto cityDto1=flightDto.getArrivalCity();
//        CityDto cityDto2=flightDto.getDepartureCity();

        flightDto.setAirline(airlineDto);
        flightDto.setArrivalCity(arrivalCity);
        flightDto.setDepartureCity(departureCity);

        flightRepository.addOrUpdate(flightDto);
        return flightDto;
    }

    public ActualWeatherDto addWeatherWithCitiesAndTemperatureAndWind(ActualWeatherDto actualWeatherDto) {

        if (actualWeatherDto == null) {
            throw new MyException("ADDING TO DB - ACTUAL WEATHER DTO IS NULL");
        }
        String cityName = actualWeatherDto.getCity().getCityName();
        CityDto cityDto = cityRepository
                .findByName(cityName)
                .orElseGet(() -> cityRepository
                        .addOrUpdate(CityDto.builder()
                                .cityId(actualWeatherDto.getCity().getCityId())
                                .airportSkyScannerid(actualWeatherDto.getCity().getAirportSkyScannerid())
                                .airportName(actualWeatherDto.getCity().getAirportName())
                                .cityName(cityName)
                                .build())
                        .orElseThrow(() -> new MyException("ADD CITY EXCEPTION")));

        actualWeatherDto.setCity(cityDto);

        actualWeatherRepository.addOrUpdate(actualWeatherDto);
        return actualWeatherDto;
    }

    public SearchDto addSearchWithFlightsAndGame(SearchDto searchDto) throws ParseException {

        if (searchDto == null) {
            throw new MyException("ADDING TO DB - SEARCH DTO IS NULL");
        }
        FlightDto currentFlight = flightRepository
                .findByName(searchDto.getChosenFlight().getAirline().getCarrierName(),
                        searchDto.getChosenFlight().getArrivalCity().getCityName(),
                        searchDto.getChosenFlight().getDepartureCity().getCityName(),
                        searchDto.getChosenFlight().getDepartureDate())
                .orElseGet(() -> addFlightWithCitiesAndAirline(searchDto.getChosenFlight()));
        FlightDto comebackFlight = null;

        if (searchDto.getComebackFlight() != null) {

            comebackFlight = flightRepository
                    .findByName(searchDto.getComebackFlight().getAirline().getCarrierName(),
                            searchDto.getComebackFlight().getArrivalCity().getCityName(),
                            searchDto.getComebackFlight().getDepartureCity().getCityName(),
                            searchDto.getComebackFlight().getDepartureDate())
                    .orElseGet(() -> addFlightWithCitiesAndAirline(searchDto.getComebackFlight()));
        }

        addWeatherWithCitiesAndTemperatureAndWind(searchDto.getActualWeather());
        String home = searchDto.getChosenMatch().getMatch().getHomeTeam().getName();
        String away = searchDto.getChosenMatch().getMatch().getAwayTeam().getName();

        TeamDto homeTeam = teamRepository
                .findByName(home)
                .orElseGet(() -> teamRepository
                        .addOrUpdate(TeamDto.builder()
                                .name(home)
                                .build())
                        .orElseThrow(() -> new MyException("ADD HOME TEAM EXCEPTION")));

        TeamDto awayTeam = teamRepository
                .findByName(away)
                .orElseGet(() -> teamRepository
                        .addOrUpdate(TeamDto.builder()
                                .name(away)
                                .build())
                        .orElseThrow(() -> new MyException("ADD AWAY TEAM EXCEPTION")));

        LocalDate date = searchDto.getChosenMatch().getMatch().getDateOfMatch();

        MatchDto matchDto = matchRepository
                .findByName(date, home, away)
                .orElseGet(() -> matchRepository
                        .addOrUpdate(MatchDto.builder()
                                .awayTeam(awayTeam)
                                .homeTeam(homeTeam)
                                .dateOfMatch(date)
                                .matchday(searchDto.getChosenMatch().getMatch().getMatchday())
                                .build())
                        .orElseThrow(() -> new MyException("ADD AIRLINE EXCEPTION")));


        LeagueDto leagueDto = leagueRepository
                .findByName(searchDto.getChosenMatch().getLeague().getCountry().getName())
                .orElseGet(() -> leagueRepository
                        .addOrUpdate(LeagueDto.builder()
                                .leagueCode(searchDto.getChosenMatch().getLeague().getLeagueCode())
                                .leagueName(searchDto.getChosenMatch().getLeague().getLeagueName())
                                .country(searchDto.getChosenMatch().getLeague().getCountry())
                                .build())

                        .orElseThrow(() -> new MyException("ADD AIRLINE EXCEPTION")));

        searchDto
                .setChosenFlight(currentFlight);
        searchDto
                .setComebackFlight(comebackFlight);

        searchDto.setChosenMatch(GameDto
                .builder()
                .match(matchDto)
                .league(leagueDto)
                .build());

        searchRepository.addOrUpdate(searchDto);
        return searchDto;
    }
}
