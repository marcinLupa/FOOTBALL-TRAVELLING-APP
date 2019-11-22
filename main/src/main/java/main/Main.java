package main;

import exceptions.MyException;
import service.MenuService;

public class Main {
    public static void main(String[] args) {
        System.out.println("JEST");
        try {
//            EntityManager entityManager = connection.DbConnection.getInstance().getEntityManager();
//            EntityTransaction entityTransaction = entityManager.getTransaction();
//
//            entityTransaction.begin();

       new MenuService().manage();

//               ActualWeatherDto aw = ActualWeatherDto
//                            .builder()
//                            .actualTime(LocalDate.now())
//                            .tempreture(
//                                    TemperatureDto
//                                    .builder()
//                                    .pressure(100.0)
//                                    .tempreture(25.9)
//                                    .build())
//                            .city(CityDto
//                                    .builder()
//                                    .airportName("WROCLAW")
//                                    .cityName("WROCLAW")
//                                    .cityId(100L)
//                                    .airportSkyScannerid("111")
//                                    .build())
//                            .wind(WindDto
//                                    .builder()
//                                    .speed(21.0)
//                                    .build())
//                            .build();
//        new ActualWatherRepositoryImpl().addOrUpdate(aw);
          //1
            //entityManager.persist(aw);
//            entityTransaction.commit();
//            connection.DbConnection.getInstance().close();
        } catch (MyException e) {
            System.err.println(e.getExceptionInfo().getMessage());
            e.printStackTrace();
        }
    }
}
//            Search s = Search
//                    .builder()
//                    .actualWeather(ActualWeather
//                            .builder()
//                            .actualTime(LocalDate.now())
//                            .temperature(Temperature.builder()
//                                    .pressure(100.0)
//                                    .tempreture(25.9)
//                                    .build())
//                            .city(City.builder()
//                                    .airportName("WROCLAW")
//                                    .nameOfCity("WROCLAW")
//                                    .airportSkyScannerId("111")
//                                    .citySkyScannerId("222")
//                                    .country("POLSKA")
//                                    .build())
//                            .wind(Wind.builder()
//                                    .speed(21.0)
//                                    .build())
//                            .build())
//                    .chosenFlight(CurrentFlight
//                            .builder()
//                            .airline(Carrier.builder()
//                                    .carrierName("SZWAGIR")
//                                    .build())
//                            .arrivalCity(PlaceFromApi.builder()
//                                    .airportName("WROCLAW")
//                                    .cityName("WROCLAW")
//                                    .build())
//                            .departureCity(PlaceFromApi.builder()
//                                    .airportName("LONDYN")
//                                    .cityName("LONDYN")
//                                    .build())
//                            .departureDate(LocalDate.parse("2019-10-12"))
//                            .ticketPrice(new BigDecimal(100))
//                            .build())
//                    .comebackFlight(CurrentFlight
//                            .builder()
//                            .airline(Carrier.builder()
//                                    .carrierName("SZWAGIR")
//                                    .build())
//                            .arrivalCity(PlaceFromApi.builder()
//                                    .airportName("LONDYN")
//                                    .cityName("LONDY")
//                                    .build())
//                            .departureCity(PlaceFromApi.builder()
//                                    .airportName("WROCLAW")
//                                    .cityName("WROCLAW")
//                                    .build())
//                            .departureDate(LocalDate.parse("2019-10-13"))
//                            .ticketPrice(new BigDecimal(100))
//                            .build())
//                    .chosenMatch(Matchday.builder()
//                            .competition(Competition.builder()
//                                    .code("11")
//                                    .competitionName("NAME")
//                                    .area(Country.builder().name("ANGLIA").build())
//                                    .build())
//                            .build())
//                    .build();
//            SearchRepositoryImpl searchRepository = new SearchRepositoryImpl();
//            searchRepository.addOrUpdate(s);

//            ActualWeather aw = ActualWeather
//                    .builder()
//                    .actualTime(LocalDate.now())
//                    .temperature(Temperature.builder()
//                            .pressure(100.0)
//                            .tempreture(25.9)
//                            .build())
//                    .city(City.builder()
//                            .airportName("WROCLAW")
//                            .nameOfCity("WROCLAW")
//                            .airportSkyScannerId("111")
//                            .citySkyScannerId("222")
//                            .country("POLSKA")
//                            .build())
//                    .wind(Wind.builder()
//                            .speed(21.0)
//                            .build())
//                    .build();
//            entityManager.persist(aw);
//            ActualWeather aw1=entityManager.find(ActualWeather.class,1L);
//      System.out.println(aw1);

//        Matchday matchday=    Matchday.builder()
//                    .competition(Competition.builder()
//                            .code("11")
//                            .competitionName("NAME")
//                            .area(Country.builder().name("ANGLIA").build())
//                            .build())
//                    .matches(List.of(Match.builder()
//                            .awayTeam(Team.builder()
//                                    .teamName("WEST HAM")
//                                    .build())
//                            .homeTeam(Team.builder()
//                                    .teamName("ARSENAL")
//                                    .build())
//                            .matchdayNumber(10)
//                            .utcDate("2019-10-12")
//                            .build()))
//                    .build();
//        entityManager.persist(matchday);

//    CurrentFlight cf=        CurrentFlight
//                    .builder()
//                    .airline(Carrier.builder()
//                            .carrierName("SZWAGIR")
//                            .build())
//                    .arrivalCity(PlaceFromApi.builder()
//                            .airportName("LONDYN")
//                            .cityName("LONDY")
//                            .build())
//                    .departureCity(PlaceFromApi.builder()
//                            .airportName("WROCLAW")
//                            .cityName("WROCLAW")
//                            .build())
//                    .departureDate(LocalDate.parse("2019-10-13"))
//                    .ticketPrice(new BigDecimal(100))
//                    .build();
//         entityManager.persist(s);

