package service;

import model.skyScanner.CityFromApi;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import service.utils.DataFromUserService;

import java.util.List;

public class WeatherAdiManagementTest {


    @Test
    public void apiWeatherSearchSearchTest() {
        WeatherApiManagmentService weatherApiManagmentService = new WeatherApiManagmentService();

        Assertions.assertNotNull(weatherApiManagmentService.apiWeatherSearch("Wroclaw"));
    }

    @Test
    public void apiPlaceSearchTest() {
        SkyScannerApiManagmentService skyScannerApiManagmentService = new SkyScannerApiManagmentService();

        Assertions.assertEquals(skyScannerApiManagmentService.apiPlaceSearch("Wroclaw"), List.of(CityFromApi.builder().nameOfCity("Wroclaw").build()));

    }
    @Test
    public void dataFromUserServiceTests(){

        DataFromUserService.getYesOrNo();

    }
}
