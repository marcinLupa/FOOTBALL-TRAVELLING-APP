package service;

import dto.ActualWeatherDto;
import dto.FlightDto;
import dto.SearchDto;
import exceptions.MyException;
import impl.*;
import model.skyScanner.CityFromApi;
import model.weather.ActualWeather;
import model.weather.ListOfWeatherFromApi;
import service.utils.DataFromUserService;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;


public class ManagementService {


    private SkyScannerApiManagmentService skyScannerApiManagmentService;
    private FootballApiManagmentService footballApiManagmentService;
    private WeatherApiManagmentService weatherApiManagmentService;
    private StatisticsService statisticsService;

    public ManagementService() {
        this.skyScannerApiManagmentService = new SkyScannerApiManagmentService();
        this.footballApiManagmentService = new FootballApiManagmentService();
        this.weatherApiManagmentService = new WeatherApiManagmentService();
        this.statisticsService=new StatisticsService(

                new FlightRepositoryImpl(),
                new AirlineRepositoryImpl(),
                new CityRepositoryImpl(),
                new GameRepositoryImpl(),
                new MatchRepositoryImpl(),
                new LeagueRepositoryImpl(),
                new SearchRepositoryImpl(),
                new TeamRepositoryImpl(),
                new ActualWatherRepositoryImpl());

    }

    public Map<FlightDto, FlightDto> flightManager() {

        Map<FlightDto, FlightDto> flights = new HashMap<>();
        try {

            FlightDto chosenFlight = skyScannerApiManagmentService.searchingFlight();

            FlightDto comeback = skyScannerApiManagmentService.comebackFlightSearch(chosenFlight);


            flights.put(chosenFlight, comeback);
        } catch (InterruptedException | URISyntaxException e) {

            throw new MyException("FLIGHT MANAGER EXCEPTION - MANAGEMENT SERVICE");
        }
        return flights;


    }

    public SearchDto matchManager() {

        SearchDto searchDto = null;
        try {
            searchDto = footballApiManagmentService.searchingMatch();
        }catch (ParseException e){
            throw new MyException("PARSE EXCEPTION");
        }
        if (searchDto == null) {
            throw new MyException("SEARCH IS NULL - MANAGEMENT SERVICE");
        }
        System.out.println(searchDto);
        return searchDto;
    }

    public ActualWeatherDto weatherCheck() {
        ActualWeatherDto actualWeather = null;
        System.out.println("PODAJ MIASTO DLA KTÓREGO CHCESZ SPRAWDZIC AKTUALNA POGODE");

        CityFromApi city = CityFromApi.builder()
                .nameOfCity(DataFromUserService.getCityNameToSearch())
                .build();

        ListOfWeatherFromApi listOfWeatherFromApi = weatherApiManagmentService
                .apiWeatherSearch(city.getNameOfCity());

        if (listOfWeatherFromApi == null) {
            throw new MyException("NO WEATHER FOR CITY FROM USER - MANAGEMENT SERVICE");
        }
        actualWeather = weatherApiManagmentService
                .actualWeatherGenerator(listOfWeatherFromApi);

        System.out.println("AKTUALNA POGODA DLA MIASTA :" + actualWeather.getCity().getCityName().toUpperCase());
        System.out.println(actualWeather);

        return actualWeather;
    }

    public void statistics() {
        System.out.println("STATYSTKI");
        statisticsService.mostPopularArrivalCity();
    }

}
