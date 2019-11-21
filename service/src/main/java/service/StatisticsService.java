package service;

import dto.AirlineDto;
import dto.CityDto;
import dto.SearchDto;
import exceptions.MyException;
import lombok.RequiredArgsConstructor;
import repositories.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor

public class StatisticsService {
    private final FlightRepository flightRepository;
    private final AirlineRepository airlineRepository;
    private final CityRepository cityRepository;
    private final GameRepository gameRepository;
    private final MatchRepository matchRepository;
    private final LeagueRepository leagueRepository;
    private final SearchRepository searchRepository;
    private final TeamRepository teamRepository;
    private final ActualWeatherRepository actualWeatherRepository;

//Metoda podająca do jakich miast najczęściej latamy i jaka jest średnia cena biletu na takie połaczenie

//Metoda podająca z jakich miast najczęściej latamy i jaka jest średnia cena biletu na takie połaczenie

// najczęściej wybierane linie lotnicze wraz z informacją o średniej cenie biletu

    public CityDto mostPopularArrivalCity() {
        CityDto arrivalCityMostPopular = flightRepository
                .arrivalMostPopularCity()
                .orElseThrow(() -> new MyException("ARRIVAL CITY MOST POPULAR EXCEPTION"));
        CityDto departureCityMostPopular = flightRepository
                .departureMostPopularCity()
                .orElseThrow(() -> new MyException("DEPARTURE CITY MOST POPULAR EXCEPTION"));
        BigDecimal avgArrivalTicketPrice=new BigDecimal(flightRepository
                .avgArrivalTicketPriceToCity(arrivalCityMostPopular)
                .orElseThrow(()->new MyException("ARRIVAL AVG TICKET PRICE EXCEPTION")));
        BigDecimal avgDepartureTicketPrice=new BigDecimal(flightRepository
                .avgDepartureTicketPriceToCity(departureCityMostPopular)
                .orElseThrow(()->new MyException("DEPARTURE AVG TICKET PRICE EXCEPTION")));
        AirlineDto mostPopularAirline=flightRepository.mostPopularAirline()
                .orElseThrow(()->new MyException("MOST POPULAR AIRLINE EXCEPTION"));
       LocalDate mostPopularDateOfFlights=flightRepository
               .mostPopularDateOfFlights()
               .entrySet().stream()
               .max(Comparator.comparing(Map.Entry::getValue))
               .map(Map.Entry::getKey)
               .orElseThrow(()->new MyException("MOST POPULAR DATE OF FLIGHT EXCEPTION"));
       CityDto maxTempCity = actualWeatherRepository.cityMaxTemp()
               .orElseThrow(()->new MyException("MOST MAX TEMP CITY EXCEPTION"));



        System.out.println(arrivalCityMostPopular);
        System.out.println(departureCityMostPopular);
        System.out.println(avgArrivalTicketPrice);
        System.out.println(avgDepartureTicketPrice);
        System.out.println(mostPopularAirline.getCarrierName());
        System.out.println(mostPopularDateOfFlights);
        return null;
    }

    public CityDto mostPopularDepartureCity() {
        System.out.println(flightRepository.departureMostPopularCity());
        return null;
    }
}
