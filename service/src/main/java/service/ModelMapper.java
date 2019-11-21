package service;

//import dto.PlaceDto;

import dto.*;
import exceptions.MyException;
import model.football.*;
import model.skyScanner.*;
import model.weather.ActualWeather;
import model.weather.Temperature;
import model.weather.Wind;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

public interface ModelMapper {

    static AirlineDto fromCarrierToAirlineDto(CarrierFromApi carrierFromApi) {
        return carrierFromApi == null ? null : AirlineDto.builder()
                .AirlineId(carrierFromApi.getCarrierId())
                .carrierName(carrierFromApi.getCarrierName())
//                .id(carrierFromApi.getIdFromDb())
                .build();
    }

    static TemperatureDto fromTemperatureToTemperatureDto(Temperature temperature) {
        return temperature == null ? null : TemperatureDto.builder()
                .id(temperature.getId())
                .tempreture(temperature.getTempreture())
                .pressure(temperature.getPressure())
                .build();
    }

    static CityDto fromCityToCityDto(CityFromApi cityFromApi) {
        return cityFromApi == null ? null : CityDto
                .builder()
                .cityId(cityFromApi.getId())
                .cityName(cityFromApi.getNameOfCity())
                .airportName(cityFromApi.getAirportName())
                .airportSkyScannerid(cityFromApi.getAirportSkyScannerId())
                .build();
    }

    static CityFromApi fromCityDtoToCity(CityDto cityDto) {
        return cityDto == null ? null : CityFromApi
                .builder()
                .id(cityDto.getCityId())
                .nameOfCity(cityDto.getCityName())
                .airportName(cityDto.getAirportName())
                .airportSkyScannerId(cityDto.getAirportSkyScannerid())
                .build();
    }

    static CityDto fromPlaceToCityDto(PlaceFromApi placeFromApi) {
        return placeFromApi == null ? null : CityDto
                .builder()
                .cityId(placeFromApi.getPlaceId())
                .cityName(placeFromApi.getCityName())
                .airportName(placeFromApi.getAirportName())
                .build();
    }

    static WindDto fromWindToWindDto(Wind wind) {
        return wind == null ? null : WindDto
                .builder()
                .id(wind.getId())
                .speed(wind.getSpeed())
                .build();
    }
    static CountryDto fromCountryToCountryDto(Country country) {
        return country == null ? null : CountryDto
                .builder()
               .name (country.getName())
                .build();
    }
    static Country fromCountryToCountryDto(CountryDto countryDto) {
        return countryDto == null ? null : Country
                .builder()
                .name (countryDto.getName())
                .build();
    }

    static LeagueDto fromCompetitionToLeagueDto(Competition competition) {
        return competition == null ? null : LeagueDto
                .builder()
                .leagueId(competition.getCompetitionId())
                .leagueName(competition.getCompetitionName())
                .country(fromCountryToCountryDto(competition.getArea()))
                .leagueCode(competition.getCode())
                .build();
    }

    static Competition fromLeagueDtoToCompetition(LeagueDto leagueDto) {
        return leagueDto == null ? null : Competition
                .builder()
                .competitionId(leagueDto.getLeagueId())
                .competitionName(leagueDto.getLeagueName())
                .area(fromCountryToCountryDto(leagueDto.getCountry()))
                .code(leagueDto.getLeagueCode())
                .build();
    }

    static MatchdayDto fromMatchdayToMatchdayDto(Matchday matchday) {
        return matchday == null ? null : MatchdayDto
                .builder()
//                .matchdayId(matchday.getMatchdayId())
                .league(fromCompetitionToLeagueDto(matchday.getCompetition()))
                .matches(fromListMatchToListMatchDto(matchday.getMatches()))
                .build();
    }

    static Matchday fromMatchdayDtoToMatchday(MatchdayDto matchdayDto) {
        return matchdayDto == null ? null : Matchday
                .builder()
//                .matchdayId(matchdayDto.getMatchdayId())
                .competition(fromLeagueDtoToCompetition(matchdayDto.getLeague()))
                .matches(fromListMatchDtoToListMatch(matchdayDto.getMatches()))
                .build();
    }

    static MatchDto fromMatchToMatchDto(Match match) {
        return match == null ? null : MatchDto
                .builder()
                .id(match.getId())
                .dateOfMatch(LocalDate.parse(
                        match.getUtcDate().replace("T"," ").replace("Z",""),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .matchday(match.getMatchday())
                .homeTeam(fromTeamToTeamDto(match.getHomeTeam()))
                .awayTeam(fromTeamToTeamDto(match.getAwayTeam()))
                .build();
    }

    static Match fromMatchDtoToMatch(MatchDto matchDto) {
        return matchDto == null ? null : Match
                .builder()
                .id(matchDto.getId())
                .utcDate(matchDto.getDateOfMatch().toString())
                .matchday(matchDto.getMatchday())
                .homeTeam(fromTeamDtoToTeam(matchDto.getHomeTeam()))
                .awayTeam(fromTeamDtoToTeam(matchDto.getAwayTeam()))
                .build();
    }

    static TeamDto fromTeamToTeamDto(Team team) {
        return team == null ? null : TeamDto
                .builder()
                .name(team.getName())
                .build();
    }

    static Team fromTeamDtoToTeam(TeamDto teamDto) {
        return teamDto == null ? null : Team
                .builder()
                .name(teamDto.getName())
                .build();
    }
    static ActualWeatherDto fromActualWeatherToActualWeatherDto(ActualWeather actualWeather) {
        return actualWeather == null ? null : ActualWeatherDto
                .builder()
                .id(actualWeather.getId())
                .tempreture(fromTemperatureToTemperatureDto(actualWeather.getTemperature()))
                .actualTime(actualWeather.getActualTime())
                .city(fromCityToCityDto(actualWeather.getCity()))
                .wind(fromWindToWindDto(actualWeather.getWind()))
                .build();
    }

    static List<MatchDto> fromListMatchToListMatchDto(List<Match> matches) {
        List<MatchDto> matchDtos = new ArrayList<>();
        matches.forEach(x -> matchDtos.add(ModelMapper.fromMatchToMatchDto(x)));
        return matchDtos;
    }
    static List<Match> fromListMatchDtoToListMatch(List<MatchDto> matchDtos) {
        List<Match> matches = new ArrayList<>();
        matchDtos.forEach(x -> matches.add(ModelMapper.fromMatchDtoToMatch(x)));
        return matches;
    }
    static FlightDto fromFlightToFlightDto(CarrierFromApi carrierFromApi, LocalDate departureDate,
                                           PlaceFromApi arrivalCity, PlaceFromApi departureCity, BigDecimal ticketPrice) {
        if (carrierFromApi == null && departureDate == null && arrivalCity == null && departureCity == null && ticketPrice == null) {
            return null;
        }
        return FlightDto
                .builder()
                .airline(fromCarrierToAirlineDto(carrierFromApi))
                .departureDate(departureDate)
                .arrivalCity(fromPlaceToCityDto(arrivalCity))
                .departureCity(fromPlaceToCityDto(departureCity))
                .ticketPrice(ticketPrice)
                .build();
    }


    //---------------
    static List<FlightDto> joiningFlightDtosLists(List<FlightFromApi> flightFromApis) {
        if (flightFromApis == null) {
            throw new MyException("FLIGHT LIST NULL - MODEL MAPPER EXCEPTION");
        }
        List<FlightDto> flightDtos = new ArrayList<>();
        flightFromApis.forEach(x -> {
            flightDtos.addAll(fromFlightToListFlightDtos(x));
        });

        LongStream.range(0, flightDtos.size())
                .forEach(i ->
                        flightDtos.get((int) i).setId(i));

        return flightDtos;
    }

    static List<FlightDto> fromFlightToListFlightDtos(FlightFromApi flightFromApi) {

        List<FlightDto> flightDtos = new ArrayList<>();

        for (QuoteFromFlightApi q : flightFromApi.getQuoteFromFlightApis()) {
            CarrierFromApi carrierFromApi = flightFromApi.getCarrierFromApis()
                    .stream()
                    .filter(carrier -> carrier
                            .getCarrierId()
                            .equals(q.getOtherDataFromQuoteApi().getCarrierIds().get(0)))
                    .findFirst()
                    .orElseThrow(() -> new MyException("WRONG CARRIER NAME MAPPING - MODEL MAPPER EXCEPTION"));

            PlaceFromApi arrivalCity = flightFromApi
                    .getPlaceFromApis()
                    .stream()
                    .filter(place -> place
                            .getPlaceId()
                            .equals(q.getOtherDataFromQuoteApi().getDestinationId()))
                    .findFirst()
                    .orElseThrow(() -> new MyException("WRONG PLACE NAME MAPPING - MODEL MAPPER EXCEPTION"));
            PlaceFromApi departureCity = flightFromApi
                    .getPlaceFromApis()
                    .stream()
                    .filter(place -> place
                            .getPlaceId()
                            .equals(q.getOtherDataFromQuoteApi().getOriginId()))
                    .findFirst()
                    .orElseThrow(() -> new MyException("WRONG PLACE NAME MAPPING - MODEL MAPPER EXCEPTION"));


            flightDtos.add(fromFlightToFlightDto(carrierFromApi,
                    LocalDate.parse(q.getOtherDataFromQuoteApi().getDepartureDate().replace("T00:00:00", "")),
                    arrivalCity, departureCity, new BigDecimal(q.getMinPrice())));
        }


        return flightDtos;

    }
}
