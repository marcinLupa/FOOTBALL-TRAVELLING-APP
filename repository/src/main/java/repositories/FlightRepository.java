package repositories;

import dto.AirlineDto;
import dto.CityDto;
import dto.FlightDto;
import generic.GenericRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FlightRepository extends GenericRepository<FlightDto> {

    Optional<FlightDto> findByName(String airlineName, String arrivalCity, String departureCity, LocalDate departureDate);
    Optional<CityDto> arrivalMostPopularCity();
    Optional<CityDto> departureMostPopularCity();
    Optional<Double> avgArrivalTicketPriceToCity(CityDto cityDto);
    Optional<Double> avgDepartureTicketPriceToCity(CityDto cityDto);
    Optional<AirlineDto> mostPopularAirline();
    Map<LocalDate, Long> mostPopularDateOfFlights();

}
