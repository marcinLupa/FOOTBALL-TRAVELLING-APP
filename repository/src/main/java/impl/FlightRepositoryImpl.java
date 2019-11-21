package impl;

import dto.AirlineDto;
import dto.CityDto;
import dto.FlightDto;
import exceptions.MyException;
import generic.AbstractGenericRepository;
import model.statistics.PopularityAndTicketPrice;
import repositories.FlightRepository;

import javax.persistence.EntityTransaction;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class FlightRepositoryImpl extends AbstractGenericRepository<FlightDto> implements FlightRepository {

    @Override
    public Optional<FlightDto> findByName(String airlineName, String arrivalCity,
                                          String departureCity, LocalDate departureDate) {

        Optional<FlightDto> op = Optional.empty();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {

            entityTransaction.begin();
            List<FlightDto> airlineDtos = entityManager
                    .createQuery("select f from FlightDto f where f.airline.carrierName=:airline " +
                                    "AND f.arrivalCity.cityName=:arrival AND  f.departureCity.cityName=:departure " +
                                    "AND f.departureDate=:date"
                            , FlightDto.class)
                    .setParameter("airline", airlineName)
                    .setParameter("arrival", arrivalCity)
                    .setParameter("departure", departureCity)
                    .setParameter("date", departureDate)
                    .getResultList();

            if (!airlineDtos.isEmpty()) {
                op = Optional.of(airlineDtos.get(0));
            }

            entityTransaction.commit();
            return op;
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new MyException("FIND BY NAME EXCEPTION");
        }

    }


    @Override
    public Optional<CityDto> arrivalMostPopularCity() {
        Optional<CityDto> op = Optional.empty();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();

            op = Optional.of(entityManager
                    .createQuery("select f.arrivalCity from FlightDto f group by f.arrivalCity.cityName order by count(f.arrivalCity.cityName) desc", CityDto.class).getResultList().get(0));

            entityTransaction.commit();
            return op;
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            e.printStackTrace();
            throw new MyException("FIND BY NAME EXCEPTION");
        }
    }

    @Override
    public Optional<CityDto> departureMostPopularCity() {
        Optional<CityDto> op = Optional.empty();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();

            op = Optional.of(entityManager
                    .createQuery("select f.departureCity from FlightDto f group by f.departureCity.cityName order by count(f.departureCity.cityName) desc", CityDto.class).getResultList().get(0));

            entityTransaction.commit();
            return op;
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            e.printStackTrace();
            throw new MyException("FIND BY NAME EXCEPTION");
        }
    }

    @Override
    public Optional<Double> avgArrivalTicketPriceToCity(CityDto cityDto) {
        Optional<Double> op = Optional.empty();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();

            op = Optional.of(entityManager
                    .createQuery("select AVG(f.ticketPrice) from FlightDto f where f.arrivalCity.id=:city", Double.class)
                    .setParameter("city", cityDto.getId()).getResultList().get(0));

            entityTransaction.commit();
            return op;
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            e.printStackTrace();
            throw new MyException("FIND BY NAME EXCEPTION");
        }
    }

    @Override
    public Optional<Double> avgDepartureTicketPriceToCity(CityDto cityDto) {
        Optional<Double> op = Optional.empty();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();

            op = Optional.of(entityManager
                    .createQuery("select AVG(f.ticketPrice) from FlightDto f where f.departureCity.id=:city", Double.class)
                    .setParameter("city", cityDto.getId()).getResultList().get(0));

            entityTransaction.commit();
            return op;
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            e.printStackTrace();
            throw new MyException("FIND BY NAME EXCEPTION");
        }
    }

    @Override
    public Optional<AirlineDto> mostPopularAirline() {
        Optional<AirlineDto> op = Optional.empty();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();

            op = Optional.of(entityManager
                    .createQuery("select MAX(f.airline) from FlightDto f ", AirlineDto.class).getSingleResult());

            entityTransaction.commit();
            return op;
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            e.printStackTrace();
            throw new MyException("FIND BY NAME EXCEPTION");
        }
    }

    @Override
    public Map<LocalDate, Long> mostPopularDateOfFlights() {

        List<Object[]> op;

        Map<LocalDate, Long> popularity = new HashMap<>();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            op = entityManager
                    .createQuery("select f.departureDate,count(f.departureDate) from FlightDto f group by f.departureDate",
                            Object[].class)
                    .getResultList();

            op.forEach(o ->
                    popularity.put((LocalDate) o[0], (Long) o[1]));

            entityTransaction.commit();
            return popularity;
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            e.printStackTrace();
            throw new MyException("FIND BY NAME EXCEPTION");
        }
    }
}

