package service;

import dto.ActualWeatherDto;
import dto.FlightDto;
import dto.SearchDto;
import exceptions.MyException;
import impl.*;
import model.skyScanner.CityFromApi;
import model.weather.ListOfWeatherFromApi;
import service.utils.DataFromUserService;
import service.utils.EmailService;

import javax.mail.MessagingException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;


 public class ManagementService {


    private SkyScannerApiManagmentService skyScannerApiManagmentService;
    private FootballApiManagmentService footballApiManagmentService;
    private WeatherApiManagmentService weatherApiManagmentService;
    private StatisticsService statisticsService;

     ManagementService() {
        this.skyScannerApiManagmentService = new SkyScannerApiManagmentService();
        this.footballApiManagmentService = new FootballApiManagmentService();
        this.weatherApiManagmentService = new WeatherApiManagmentService();
        this.statisticsService = new StatisticsService(

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

     Map<FlightDto, FlightDto> flightManager() {

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

        SearchDto searchDto;
        try {
            searchDto = footballApiManagmentService.searchingMatch();
        } catch (ParseException e) {
            throw new MyException("PARSE EXCEPTION");
        }
        if (searchDto == null) {
            throw new MyException("SEARCH IS NULL - MANAGEMENT SERVICE");
        }
        System.out.println(searchDto);


        System.out.println("CZY PRZESLAC DANE NA SKRZYNKE MAIL W FORMACIE JSON?");
        if (DataFromUserService.getYesOrNo()) {
            System.out.println("PODAJ ADRES EMAIL");
            String email = DataFromUserService.getEmail();

            EmailService emailService = new EmailService();
            try {
                emailService.sendAsHtml(email
                        , "APLIKACJA DLA FANOW FOOTBALLU I PODROZNIKOW!!!  ",

                        "<head>  " +
                                "<body>" + "<h1>" + "TWOJA PODROZ:" + "," + "</h1>" +
                                "TWOJ WYBRANY MECZ NA KTORY CHCESZ LECIEC TO: " + "<p>" +
                                searchDto.getChosenMatch() + "</p>" +
                                "<p>" + "POLECISZ SAMOLOTEM: " + "</p>" +
                                "<p>"+ searchDto.getChosenFlight() + " " +
                                searchDto.getComebackFlight() + "</p>" +
                                "<p>" +"AKTUALNA POGODA W " + searchDto.getChosenFlight().getArrivalCity().getCityName().toUpperCase() + "</p>" +
                                "<p>" +searchDto.getActualWeather() + "</p>" +
                                "</body>" +
                                "</html>");
            } catch (MessagingException e) {
                throw new MyException( "E-MAIL SEND AS HTML EXCEPTION" + e.getMessage());
            }
        }

        return searchDto;
    }

     ActualWeatherDto weatherCheck() {
        ActualWeatherDto actualWeather;
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

     void statistics() {


    }


}
