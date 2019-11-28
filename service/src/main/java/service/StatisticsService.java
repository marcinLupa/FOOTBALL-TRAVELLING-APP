package service;

import dto.AirlineDto;
import dto.CityDto;
import exceptions.MyException;
import lombok.RequiredArgsConstructor;
import repositories.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;

@RequiredArgsConstructor

public class StatisticsService {
    private final FlightRepository flightRepository;
    private final ActualWeatherRepository actualWeatherRepository;


    public CityDto mostPopularArrivalCity() {
        return flightRepository
                .arrivalMostPopularCity()
                .orElseThrow(() -> new MyException("ARRIVAL CITY MOST POPULAR EXCEPTION"));
    }

    public CityDto departureCityMostPopular() {
        return flightRepository
                .departureMostPopularCity()
                .orElseThrow(() -> new MyException("DEPARTURE CITY MOST POPULAR EXCEPTION"));
    }

    public BigDecimal avgArrivalTicketPrice() {
        return new BigDecimal(flightRepository
                .avgArrivalTicketPriceToCity(mostPopularArrivalCity())
                .orElseThrow(() -> new MyException("ARRIVAL AVG TICKET PRICE EXCEPTION")));
    }


    public BigDecimal avgDepartureTicketPrice() {
        return new BigDecimal(flightRepository
                .avgDepartureTicketPriceToCity(departureCityMostPopular())
                .orElseThrow(() -> new MyException("DEPARTURE AVG TICKET PRICE EXCEPTION")));
    }


    public AirlineDto mostPopularAirline() {
        return flightRepository.mostPopularAirline()
                .orElseThrow(() -> new MyException("MOST POPULAR AIRLINE EXCEPTION"));
    }


    public LocalDate mostPopularDateOfFlights() {
        return flightRepository
                .mostPopularDateOfFlights()
                .entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new MyException("MOST POPULAR DATE OF FLIGHT EXCEPTION"));
    }

    public CityDto maxTempCity() {
        return actualWeatherRepository.cityMaxTemp()
                .orElseThrow(() -> new MyException("MOST MAX TEMP CITY EXCEPTION"));
    }


    public CityDto mostPopularDepartureCity() {
        System.out.println(flightRepository.departureMostPopularCity());
        return null;
    }
}
