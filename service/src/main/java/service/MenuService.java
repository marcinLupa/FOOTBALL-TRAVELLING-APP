package service;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.MyException;
import service.utils.DataFromUserService;

public class MenuService {
    private ManagementService managementService;

    public MenuService() {

        this.managementService = new ManagementService();
    }

    public void manage() {
        try {
            while (true) {

                System.out.println("MENU GŁOWNE: " + "\n");

                System.out.println("JAKIE DANE CHCESZ EDYTOWAC LUB WYSWIETLIC?" + "\n" +
                        "1 - GŁOWNY PROGRAM DO WYSZUKIWANIA MECZOW PLKRASKICH, POLACZEN LOTNICZYCH I AKTUALNEJ POGODY" + "\n" +
                        "2 - WYSZUKIWARKA LOTOW" + "\n" +
                        "3 - SPRAWDZ POGODE " + "\n" +
                        "4 - STATYSTKI WYSZUKIWAN" + "\n" +
                        "5 - WYJSCIE Z PROGRAMU");

                int menuOption = DataFromUserService.getInt(6);
                switch (menuOption) {
                    case 1 -> managementService.matchManager();
                    case 2 -> managementService.flightManager();
                    case 3 -> managementService.weatherCheck();
                    case 4 -> managementService.statistics();
                    case 5 -> {
                        return;
                    }
                }
            }
        } catch (MyException e) {
            e.printStackTrace();
            System.err.println(e.getExceptionInfo().getMessage());
            System.err.println(e.getExceptionInfo().getExceptionTime());
            manage();
        }
    }

    private static <T> String toJson(T t) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(t);
    }
}