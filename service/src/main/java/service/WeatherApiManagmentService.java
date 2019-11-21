package service;

import dto.ActualWeatherDto;
import dto.CityDto;
import exceptions.MyException;
import impl.*;
import json.impl.WeatherSearchApiJsonConverter;
import model.skyScanner.CityFromApi;
import model.weather.*;
import apiConnection.apiServices.ApiService;
import apiConnection.apiServices.ApiServiceWeatherSearch;

import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class WeatherApiManagmentService {

    private final String[] headersKeyWeatherHost = {"x-rapidapi-host", "community-open-weather-map.p.rapidapi.com"};
    private final String[] headersKeyWeatherKey = {"x-rapidapi-key", "876e3b9efdmshd10d7264ee173e1p15ba74jsn6c2ede0a7c73"};

    private final String urlWeather = "https://community-open-weather-map.p.rapidapi.com/forecast?q={0}";
    private final ApiService<ListOfWeatherFromApi> weatherSearchApiService;
    private SkyScannerApiManagmentService skyScannerApiManagmentService;

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

    public WeatherApiManagmentService() {
        this.weatherSearchApiService = new ApiServiceWeatherSearch();
        this.skyScannerApiManagmentService = new SkyScannerApiManagmentService();
    }

    public ListOfWeatherFromApi apiWeatherSearch(String city) {

        if (city == null) {
            throw new MyException("NULL CITY EXCEPTION - WEATHER SERVICE");
        }
        String cityEnglishOrNot = null;
        if (skyScannerApiManagmentService.apiPlaceSearch(
                SkyScannerApiManagmentService
                        .urlCitySearchEnglishCity(city)).isEmpty()) {
            cityEnglishOrNot = skyScannerApiManagmentService.apiPlaceSearch(
                    SkyScannerApiManagmentService.urlCitySearch(city)).get(0).getNameOfCity();
        } else {
            cityEnglishOrNot = skyScannerApiManagmentService.apiPlaceSearch(
                    SkyScannerApiManagmentService
                            .urlCitySearchEnglishCity(city)).get(0).getNameOfCity();
        }

        return weatherSearchApiService.getDataFromApi(
                urlMatchSearch(cityEnglishOrNot)
                , new WeatherSearchApiJsonConverter()
                , headersKeyWeatherHost
                , headersKeyWeatherKey);


    }

    private String urlMatchSearch(String city) {

        Object[] objArray = {city};
        return new MessageFormat(urlWeather).format(objArray);
    }

    ActualWeatherDto actualWeatherGenerator(ListOfWeatherFromApi listOfWeatherFromApi) {

        if (listOfWeatherFromApi == null) {
            throw new MyException("WEATHER SEARCH IS NULL EXCEPTION - WEATHER SERVICE");
        }
        Temperature temperature = listOfWeatherFromApi
                .getWeatherList()
                .stream()
                .filter(x -> LocalDate.parse(x.getDateOfMeasurement().split(" ")[0]).equals(LocalDate.now()) ||
                        LocalDate.parse(x.getDateOfMeasurement().split(" ")[0]).equals(LocalDate.now().plusDays(1)))
                .findFirst()
                .map(Weather::getMain)
                .orElseThrow(() -> new MyException("THERE IS NO TEMPRETURE FOR TODAY EXCEPTION - WEATHER SERVICE"));
        LocalDate actualTime = listOfWeatherFromApi
                .getWeatherList()
                .stream()
                .filter(x -> LocalDate.parse(x.getDateOfMeasurement().split(" ")[0]).equals(LocalDate.now()) ||
                        LocalDate.parse(x.getDateOfMeasurement().split(" ")[0]).equals(LocalDate.now().plusDays(1)))
                .findFirst()
                .map(time -> LocalDate.parse(time.getDateOfMeasurement().split(" ")[0]))
                .orElseThrow(() -> new MyException("THERE IS NO WEATHER ON TODAY EXCEPTION - WEATHER SERVICE"));

        Wind wind = listOfWeatherFromApi
                .getWeatherList()
                .stream()
                .filter(x -> LocalDate.parse(x.getDateOfMeasurement().split(" ")[0]).equals(LocalDate.now()) ||
                        LocalDate.parse(x.getDateOfMeasurement().split(" ")[0]).equals(LocalDate.now().plusDays(1)))
                .findFirst()
                .map(Weather::getWind)
                .orElseThrow(() -> new MyException("THERE IS NO TEMPRETURE FOR TODAY EXCEPTION - WEATHER SERVICE"));


        ActualWeatherDto actualWeatherDto = ModelMapper.fromActualWeatherToActualWeatherDto(ActualWeather
                .builder()
                .actualTime(actualTime)
                .city(listOfWeatherFromApi.getCity())
                .temperature(Temperature.builder()
                        .id(temperature.getId())
                        .pressure(temperature.getPressure())
                        .tempreture(temperature.getTempreture())
                        .builder())
                .wind(Wind.builder()
                        .id(wind.getId())
                        .speed(wind.getSpeed())
                        .build())
                .build());

        addingToDatebaseService.addWeatherWithCitiesAndTemperatureAndWind(actualWeatherDto);
        return actualWeatherDto;

//        actualWeather.getTemperature().setTemperature(actualWeather.getTemperature().getTempreture());
//        actualWeather.getWind().setSpeed(actualWeather.getWind().getSpeed());

    }
}
