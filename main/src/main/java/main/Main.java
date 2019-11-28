package main;

import dto.ActualWeatherDto;
import dto.FlightDto;
import dto.SearchDto;
import service.ManagementService;
import service.MenuService;

public class Main {
    public static void main(String[] args) {
        System.out.println(ManagementService.toJson(SearchDto.builder().chosenFlight(FlightDto.builder().build()).comebackFlight(FlightDto.builder().build()).actualWeather(ActualWeatherDto.builder().build())));
        new MenuService().manage();

    }
}
