package service;

import dto.FlightDto;
import exceptions.MyException;
import impl.*;
import json.impl.FlightApiJsonConverter;
import json.impl.PlaceSearchApiJsonConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import model.skyScanner.CityFromApi;
import model.skyScanner.CityListFromApi;
import model.skyScanner.FlightFromApi;
import service.api.apiServices.ApiServiceFlightImpl;
import service.api.apiServices.ApiServicePlaceSearchImpl;
import service.utils.DataFromUserService;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@AllArgsConstructor
@Data
@Builder

public class SkyScannerApiManagmentService {

    private final static String urlPlane = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browseroutes/v1.0/PL/PLN/pl_PL/{0}/{1}/{2}";//
    private final static String urlCity = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/autosuggest/v1.0/PL/PLN/pl_PL/?query={0}";
    private final static String urlCityEnglish = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/autosuggest/v1.0/UK/PLN/en_GB/?query={0}";
    private final static String urlCountry = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/autosuggest/v1.0/GB/PLN/gb_GB/?query={0}";

    private static final String[] headersHostSkyScanner = {"X-RapidAPI-Host", "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com"};
    private static final String[] headersKeySkyScanner = {"X-RapidAPI-Key", "876e3b9efdmshd10d7264ee173e1p15ba74jsn6c2ede0a7c73"};

    private ApiServicePlaceSearchImpl apiServicePlaceSearch;
    private ApiServiceFlightImpl apiServiceFlight;
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

    public SkyScannerApiManagmentService() {
        this.apiServicePlaceSearch = new ApiServicePlaceSearchImpl();
        this.apiServiceFlight = new ApiServiceFlightImpl();
    }

    public FlightDto searchingFlight() throws MyException, URISyntaxException, InterruptedException {

        System.out.println("PODAJ MIASTO Z KTÓREGO BĘDZIESZ LECIAŁ(BEZ POLSKICH ZNAKOW)");
        CityFromApi cityDeparture = CityFromApi.builder()
                .airportName(DataFromUserService.getCityNameToSearch())
                .build();

        System.out.println("PODAJ MIASTO DO KTÓREGO BĘDZIESZ LECIAŁ(BEZ POLSKICH ZNAKOW)");
        CityFromApi cityArrival = CityFromApi.builder()
                .airportName(DataFromUserService.getCityNameToSearch())
                .build();

        LocalDate dateOfFlight;

        System.out.println("CZY CHCESZ ZNALEZC LOTY W DNIU DZISIEJSZYM");
        if (DataFromUserService.getYesOrNo()) {
            dateOfFlight = LocalDate.now();


        } else {
            System.out.println("PODAJ DATE DLA KTOREJ MAM SZUKAC POLACZEN LOTNICZYCH");
            dateOfFlight = DataFromUserService.getLocalDate();

            if (dateOfFlight.isBefore(LocalDate.now())) {
                throw new MyException("DATE FROM PAST EXCEPTION - SKY SCANNER SERVICE");
            }
        }

        List<FlightFromApi> flightFromApis = listOfFlightGenerator(cityDeparture.getAirportName(), cityArrival.getAirportName(), dateOfFlight);


        List<FlightDto> flightDtos = ModelMapper.joiningFlightDtosLists(
                flightFromApis)
                .stream()
                .filter(x -> x.getDepartureDate().equals(dateOfFlight))
                .collect(toList());

        if (flightDtos.isEmpty()) {

            System.out.println("BRAK LOTOW DLA PODANYCH DANYCH LUB WSZYTSKIE BILETY ZOSTALY SPRZEDANE" + "\n" +
                    "CZY CHCESZ WYSZUKAC INNY LOT?");
            if (DataFromUserService.getYesOrNo()) {
                searchingFlight();
            } else
                return null;
        }
        System.out.println("WYBRANE LOTY W PODANYM TERMINIE: ");
        flightDtos.forEach(System.out::println);

        System.out.println("CZY CHCESZ FILTROWAC WYNIKI?");
        if (DataFromUserService.getYesOrNo()) {
            flightDtos = filtratingManager(flightDtos);
        }

        System.out.println(" WPISZ  NUMER LOTU PONIZEJ ");
        Long fromUser = (long) DataFromUserService.getInt();

        FlightDto flightDto = flightDtos
                .stream()
                .filter(x -> x.getId().equals(fromUser))
                .findFirst()
                .orElseThrow(() -> new MyException("WRONG NUMBER OF FLIGHT EXCEPTION - SKY SCANNER SERVICE"));

        System.out.println(flightDto);
        addingToDatebaseService.addFlightWithCitiesAndAirline(flightDto);

        return flightDto;


    }

    FlightDto comebackFlightSearch(FlightDto flightDto) throws URISyntaxException, InterruptedException {
        if (flightDto == null) {
            throw new MyException("CURRENT FLIGHT IS NULL EXCEPTION - SKY SCANNER SERVICE");

        }
        FlightDto comeBackFlightDto = null;
        System.out.println("CZY CHCESZ SPRAWDZIC LOTY POWROTNE?");
        if (DataFromUserService.getYesOrNo()) {
            List<FlightDto> comebackFlights = comebackFlightsGenerator(flightDto).stream()
                    .filter(x -> x.getDepartureDate().equals(flightDto.getDepartureDate())
                            || x.getDepartureDate().isAfter(flightDto.getDepartureDate())
                            && x.getDepartureDate().isBefore(flightDto.getDepartureDate().plusDays(7)))
                    .collect(toList());
            if (comebackFlights.isEmpty()) {
                System.out.println("BRAK LOTOW DLA PODANYCH DANYCH LUB WSZYTSKIE BILETY ZOSTALY SPRZEDANE" + "\n" +
                        "CZY CHCESZ WYSZUKAC INNY LOT?");
                if (DataFromUserService.getYesOrNo()) {
                    searchingFlight();
                } else {
                    return null;
                }
            } else {
                System.out.println("OTO WYBRANE POLACZENIA DO MAKSYMALNIE TYGODNIA OD WYDARZENIA:");
                comebackFlights.forEach(System.out::println);
                System.out.println("CZY CHCESZ JESZCZE FILTROWAC WYNIKI?");
                if (DataFromUserService.getYesOrNo()) {
                    comebackFlights = filtratingManager(comebackFlights);
                }
                System.out.println("CZY WYBIERASZ KTORES Z POWYZSZYCH POLACZEN?");
                if (DataFromUserService.getYesOrNo()) {
                    System.out.println("PODAJ NUMER WYBRANEGO LOTU: ");
                    Long fromUSer = DataFromUserService.getLong();

                    comeBackFlightDto = comebackFlights
                            .stream()
                            .filter(x -> x.getId().equals(fromUSer))
                            .findFirst()
                            .orElseThrow(() -> new MyException("GIVEN FLIGHT ID IS WRONG EXCEPTION - SKY SCANNER SERVICE"));
                } else {
                    comeBackFlightDto = null;
                    searchingFlight();
                }
            }
        } else {
            return null;
        }
        return comeBackFlightDto;
    }

    List<FlightDto> comebackFlightsGenerator(FlightDto flightDto) throws URISyntaxException, InterruptedException {

        return ModelMapper
                .joiningFlightDtosLists(
                        listOfFlightGenerator(flightDto.getArrivalCity().getCityName()
                                , flightDto.getDepartureCity().getCityName()
                                , flightDto.getDepartureDate()));
    }

    List<CityFromApi> apiPlaceSearch(String place) {

        CityListFromApi cityListFromApi = apiServicePlaceSearch.getDataFromApi(
                place,
                new PlaceSearchApiJsonConverter(),
                headersHostSkyScanner,
                headersKeySkyScanner
        );


        return cityListFromApi.getCities();

    }

    List<FlightFromApi> listOfFlightGenerator(String cityDeparture, String cityArrival, LocalDate dateOfFlight) {

        List<FlightFromApi> flightFromApis = new ArrayList<>();

        List<String> listOfArrivalCities = apiPlaceSearch(SkyScannerApiManagmentService
                .urlCitySearch(cityArrival))
                .stream()
                .map(CityFromApi::getAirportSkyScannerId)
                .collect(Collectors.toList());

        String cityFromUser =
                apiPlaceSearch(
                        SkyScannerApiManagmentService
                                .urlCitySearch(
                                        cityDeparture)).get(0).getAirportSkyScannerId();
        try {
            listOfArrivalCities.forEach(arrival -> {

                flightFromApis.add(apiFlightSearch(cityFromUser,
                        arrival,
                        dateOfFlight.toString()));

            });
        } catch (MyException e) {
            if (e.getExceptionInfo()
                    .getMessage()
                    .startsWith("VALUE FROM API VALIDATION ERROR")) {

                flightFromApis.add(apiFlightSearch(cityFromUser,
                        listOfArrivalCities.get(0),
                        dateOfFlight.toString()));

                return flightFromApis;

            } else {
                System.out.println(e.getExceptionInfo().getMessage());
            }
        }

        return flightFromApis;

    }

    FlightFromApi apiFlightSearch(String arrivalCity, String departurePlace, String dateOfFlight) {
        FlightFromApi flightFromApi = null;

            flightFromApi = apiServiceFlight.getDataFromApi(
                    urlPlaneSearch(arrivalCity, departurePlace, dateOfFlight),
                    new FlightApiJsonConverter(),
                    headersHostSkyScanner,
                    headersKeySkyScanner);


        return flightFromApi;
    }

    static String urlCitySearch(String city) {

        Object[] objArray = {city};
        return new MessageFormat(urlCity).format(objArray);
    }

    static String urlCitySearchEnglishCity(String city) {

        Object[] objArray = {city};
        return new MessageFormat(urlCityEnglish).format(objArray);
    }

    static String urlCountrySearch(String city) {

        Object[] objArray = {city};
        return new MessageFormat(urlCountry).format(objArray);
    }

    private String urlPlaneSearch(String arrivalCity, String departurePlace, String dateOfFlight) {

        Object[] objArray = {arrivalCity, departurePlace, dateOfFlight};
        return new MessageFormat(urlPlane).format(objArray);
    }

    static List<FlightDto> filtratingManager(List<FlightDto> flightDtos) {

        List<FlightDto> filtredFlights = new ArrayList<>();
        System.out.println("WYBIERZ FILTR: " + "\n" +
                "1 - PO NAZWIE LINI LOTNICZEJ" + "\n" +
                "2 - PO DACIE WYLOTU" + "\n" +
                "3 - PO CENIE BILETU");

        int option = DataFromUserService.getInt(4);
        switch (option) {
            case 1:
                System.out.println("PODAJ NAZWE SZUKANYCH LINI LOTNICZYCH: ");

                String airlines = DataFromUserService.getStringLettersAndDigits();

                filtredFlights = flightDtos
                        .stream()
                        .filter(x -> x.getAirline().getCarrierName().equalsIgnoreCase(airlines))
                        .collect(toList());
                break;
            case 2:
                System.out.println("PODAJ DATE OD KTOREJ MAM SZUKAC POLACZEN LOTNICZYCH");
                LocalDate dateFrom = DataFromUserService.getLocalDate();

                if (dateFrom.isBefore(LocalDate.now())) {
                    throw new MyException("DATE FROM PAST EXCEPTION - SKY SCANNER SERVICE");
                }

                System.out.println("PODAJ DATE DO KTOREJ MAM SZUKAC POLACZEN LOTNICZYCH");
                LocalDate dateTo = DataFromUserService.getLocalDate();

                if (dateTo.isBefore(dateFrom)) {
                    throw new MyException("DATE IS EARLIER THEN DATE FROM EXCEPTION - SKY SCANNER SERVICE");
                }
                if (dateFrom.equals(dateTo)) {
                    filtredFlights = flightDtos
                            .stream()
                            .filter(x -> x.getDepartureDate().equals(dateFrom))
                            .collect(toList());
                } else {
                    filtredFlights = flightDtos
                            .stream()
                            .filter(x -> x.getDepartureDate().isAfter(dateFrom) &&
                                    x.getDepartureDate().isBefore(dateTo))
                            .collect(toList());
                }
                break;
            case 3:
                System.out.println("PODAJ MAKSYMALNA KWOTE JAKA CHCESZ DAC ZA BILET: ");
                BigDecimal maxPrice = DataFromUserService.getPrice();
                if (flightDtos
                        .stream()
                        .noneMatch(x -> x.getTicketPrice().compareTo(maxPrice) < 0)) {
                    throw new MyException("PRICE FOR TICKET IS UNDER CHEAPEST TICKET EXCEPTION - SKY SCANNER SERVICE");
                }
                filtredFlights = flightDtos
                        .stream()
                        .filter(x -> x.getTicketPrice().compareTo(maxPrice) < 0)
                        .collect(toList());
                break;
        }

        filtredFlights.forEach(System.out::println);

        System.out.println("CZY CHCESZ JESZCZE FILTROWAC WYNIKI?");
        if (DataFromUserService.getYesOrNo()) {
            filtratingManager(filtredFlights);
        }
        return filtredFlights;
    }
}