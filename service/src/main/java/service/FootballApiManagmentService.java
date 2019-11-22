package service;

import dto.*;
import exceptions.MyException;
import impl.*;
import json.impl.ListStringJsonFileConverter;
import json.impl.MatchdayApiJsonConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import model.football.Competition;
import model.football.Matchday;
import model.skyScanner.CityFromApi;
import model.skyScanner.FlightFromApi;
import service.api.apiServices.ApiServiceMatchdayImpl;
import service.utils.DataFromUserService;

import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.toList;

@Data
@Builder
@AllArgsConstructor
class FootballApiManagmentService {

    private final static String urlFootballMatches = "https://api.football-data.org/v2/competitions/{0}/matches?matchday={1}";

    private static final String[] headersKeyFootballApi = {"X-Auth-Token", "57f6b438c1bd454db5880bcb88861776"};

    private static final String codesName = "json/src/main/resources/codes.json";

    private final ApiServiceMatchdayImpl apiServiceMatchday;

    private SkyScannerApiManagmentService skyScannerApiManagmentService;

    private WeatherApiManagmentService weatherApiManagmentService;

    private AddingToDatebaseService addingToDatebaseService = new AddingToDatebaseService(

            new FlightRepositoryImpl(),
            new AirlineRepositoryImpl(),
            new CityRepositoryImpl(),
            new GameRepositoryImpl(),
            new MatchRepositoryImpl(),
            new LeagueRepositoryImpl(),
            new SearchRepositoryImpl(),
            new TeamRepositoryImpl(),
            new ActualWatherRepositoryImpl()
    );

    FootballApiManagmentService() {
        this.apiServiceMatchday = new ApiServiceMatchdayImpl();
        this.skyScannerApiManagmentService = new SkyScannerApiManagmentService();
        this.weatherApiManagmentService = new WeatherApiManagmentService();
    }


    SearchDto searchingMatch() throws ParseException {
        GameDto chosenMatch = null;
        FlightDto choosenFlight = null;
        FlightDto comeback = null;
        ActualWeatherDto actualWeather = null;
        try {


            System.out.println("LISTA LIG: ");

            List<LeagueDto> leagueDtos = listOfCompetitionsFromJson(codesName);

            leagueDtos.forEach(System.out::println);

            System.out.println("WYBIERZ LIGE I PODAJ JEJ ID:");

            int leagueFromUser = DataFromUserService.getInt(leagueDtos.size() - 1);

            System.out.println("DLA KTOREJ KOLEJKI LIGOWEJ CHCESZ SPRAWDZIC POLACZENIA?");

            int matchdayInt = DataFromUserService.getInt();
            MatchdayDto matchdayDto = null;
                matchdayDto = ModelMapper.fromMatchdayToMatchdayDto(apiMatchSearch(ModelMapper.fromLeagueDtoToCompetition(leagueDtos.get(leagueFromUser)), matchdayInt));


            System.out.println("---");
            matchdayDto.getMatches().forEach(System.out::println);

            new MatchdayDtoValidator().validate(matchdayDto);

            System.out.println(matchdayDto);

            System.out.println("PODAJ NUMER MECZU DLA KTOREGO MAM SPRAWDZIC POLACZENIE");

            chosenMatch = GameDto
                    .builder()
                    .league(matchdayDto.getLeague())
                    .match(matchdayDto.getMatches().get(DataFromUserService.getInt(matchdayDto.getMatches().size() - 1)))
                    .build();
            System.out.println("WYBRALES MECZ:" + chosenMatch);

            System.out.println("PODAJ MIASTO Z KTOREGO MAM SZUKAC LOTOW");

            CityDto city = ModelMapper.fromCityToCityDto(CityFromApi
                    .builder()
                    .airportName(DataFromUserService.getCityNameToSearch())
                    .build());


            GameDto finalChosenMatch = chosenMatch;
            List<FlightDto> flightDtos = ModelMapper.joiningFlightDtosLists(
                    listOfFlightGenerator(chosenMatch, city))
                    .stream()
                    .filter(x -> x.getDepartureDate()
                            .equals(finalChosenMatch.getMatch().getDateOfMatch()))
                    .collect(toList());

            if (flightDtos.isEmpty()) {
                throw new MyException("THERE IS NO FLIGHTS AT THIS TIME");
            }
            flightDtos.forEach(System.out::println);

            Long numberOfFlight;
            List<FlightDto> flightDtoFiltred;

            System.out.println("CZY CHCESZ FILTROWAC WYNIKI?");
            if (DataFromUserService.getYesOrNo()) {
                flightDtoFiltred = SkyScannerApiManagmentService.filtratingManager(flightDtos);
                flightDtoFiltred.forEach(System.out::println);
                System.out.println("CZY WYBRALES JUZ SWOJ LOT(WPISZ TAK) CZY CHCESZ WYJSC DO MENU GLOWNEGO(WPISZ NIE) : ");
                if (DataFromUserService.getYesOrNo()) {
                    System.out.println("PODAJ NUMER WYBRANEGO LOTU: ");
                    numberOfFlight = DataFromUserService.getLong();
                } else {
                    return null;
                }

            } else {
                System.out.println("JEZELI WYBRALES JUZ SWOJ LOT WPISZ TAK, JEZELI CHCESZ WYJSC DO MENU GLOWNEGO WPISZ NIE: ");
                if (DataFromUserService.getYesOrNo()) {
                    System.out.println("PODAJ NUMER WYBRANEGO LOTU: ");
                    numberOfFlight = DataFromUserService.getLong();
                } else
                    return null;

            }
            System.out.println("TWOJ WYBRANY LOT POWROTNY TO:");
            choosenFlight = flightDtos
                    .stream()
                    .filter(x -> x.getId().equals(numberOfFlight))
                    .findFirst()
                    .orElseThrow(() -> new MyException("WRONG NUMBER OF FLIGHT EXCEPTION - FOOTBALL SERVICE"));

            System.out.println(choosenFlight);
            comeback = skyScannerApiManagmentService.comebackFlightSearch(choosenFlight);
            if (comeback != null) {
                System.out.println("TWOJ WYBRANY LOT TO:");
                System.out.println(comeback);
            }

            actualWeather = weatherApiManagmentService
                    .actualWeatherGenerator(weatherApiManagmentService
                            .apiWeatherSearch(choosenFlight.getArrivalCity().getCityName()));
        } catch (InterruptedException | URISyntaxException e) {
            throw new MyException("FOTBALL SERVICE");

        }

        SearchDto searchDto = SearchDto.builder()
                .actualWeather(actualWeather)
                .chosenFlight(choosenFlight)
                .comebackFlight(comeback)
                .chosenMatch(chosenMatch)
                .build();
        System.out.println(searchDto.getChosenMatch().getMatch().getAwayTeam());

        addingToDatebaseService.addSearchWithFlightsAndGame(searchDto);

        return searchDto;
    }


    private List<FlightFromApi> listOfFlightGenerator(GameDto gameDto, CityDto cityDto) {

        CityFromApi cityDeparture = ModelMapper.fromCityDtoToCity(cityDto);
        List<FlightFromApi> flightFromApis = new ArrayList<>();

        List<String> allCitesInSearch = skyScannerApiManagmentService
                .apiPlaceSearch(SkyScannerApiManagmentService
                        .urlCountrySearch(gameDto.getLeague().getCountry().getName()))
                .stream()
                .map(CityFromApi::getAirportSkyScannerId).collect(Collectors.toList());

        String cityFromUser =
                skyScannerApiManagmentService.apiPlaceSearch(
                        SkyScannerApiManagmentService
                                .urlCitySearch(
                                        cityDeparture.getAirportName())).get(0).getAirportSkyScannerId();
        allCitesInSearch.forEach(System.out::println);
        try {
            allCitesInSearch.forEach(cityArrival -> {
                flightFromApis.add(skyScannerApiManagmentService.apiFlightSearch(cityFromUser,
                        cityArrival,
                        gameDto.getMatch().getDateOfMatch().toString()));
            });

        } catch (MyException e) {
            if (Pattern.compile("ValidationErrors").matcher(e.getExceptionInfo().getMessage()).find()) {
                if (flightFromApis.isEmpty()) {
                    flightFromApis.add(skyScannerApiManagmentService.apiFlightSearch(cityFromUser,
                            allCitesInSearch.get(0),
                            gameDto.getMatch().getDateOfMatch().toString()));
                } else {
                    return flightFromApis;
                }
            }
        }
        return flightFromApis;
    }


    private Matchday apiMatchSearch(Competition competition, int matchdayInt) {

        Matchday matchday = null;
        matchday = apiServiceMatchday.getDataFromApi(
                urlMatchSearch(competition, matchdayInt)
                , new MatchdayApiJsonConverter()
                , headersKeyFootballApi
                , headersKeyFootballApi);

//        System.out.println(apiServiceMatchday.getDataFromApi(
//                urlMatchSearch(competition, matchdayInt)
//                , new MatchdayApiJsonConverter()
//                , headersKeyFootballApi
//                , headersKeyFootballApi));

        Matchday matchdayToRange = matchday;
        LongStream.range(0, matchday.getMatches().size())
                .forEach(i ->
                        matchdayToRange
                                .getMatches().get((int) i).setId(i));
        return matchday;
    }

    private String urlMatchSearch(Competition competition, int matchday) {

        Object[] objArray = {competition.getCode(), matchday};
        return new MessageFormat(urlFootballMatches).format(objArray);
    }

    private static List<LeagueDto> listOfCompetitionsFromJson(String filename) {

        return new ListStringJsonFileConverter()
                .fromJson(filename)
                .orElseThrow(() -> new MyException("LIST OF COMPETITIONS FROM JSON EXCEPTION - FOOTBALL SERVICE"))
                .stream()
                .map(x -> ModelMapper.fromCompetitionToLeagueDto(Competition
                        .builder()
                        .competitionId((Long.valueOf(x.split(";")[0])))
                        .code(x.split(";")[1])
                        .competitionName(x.split(";")[2])
                        .build()))
                .collect(toList());
    }

    private static String cityFromTeamName(Matchday chosenMatch, List<FlightDto> finalFlightDtos) {
        if (chosenMatch == null) {
            throw new MyException("MATCHDAY IS NULL EXCEPTION- FOOTBALL SERVICE");
        }
        if (finalFlightDtos == null) {
            throw new MyException("CURRENT FLIGHTS ARE NULL EXCEPTION- FOOTBALL SERVICE");
        }

        return chosenMatch
                .getMatches()
                .stream()
                .map(hometeam ->
                        Arrays.stream(hometeam.getHomeTeam()
                                .getName()
                                .split(" "))
                                .filter(x -> finalFlightDtos
                                        .stream()
                                        .map(y -> y.getArrivalCity().getCityName())
                                        .collect(Collectors.toList())
                                        .contains(x))
                                .findFirst()
                                .orElse("THERE IS NO SUCH CITY EXCEPTION- FOOTBALL SERVICE"))
                .findFirst()
                .orElse(("THERE IS NO SUCH CITY EXCEPTION- FOOTBALL SERVICE"));
    }
}
