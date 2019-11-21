package model.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import model.skyScanner.CityFromApi;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

//@Entity

public class ActualWeather {
//    @Id
//    @GeneratedValue
    private Long id;
//    @ManyToOne(cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "tempreture_id")
    private Temperature temperature;
//    @ManyToOne(cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "wind_id")
    private Wind wind;

    private LocalDate actualTime;
//    @ManyToOne(cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "city_id")
    private CityFromApi city;

//    @Override
//    public String toString() {
//        return "TEMPERTURA POWIETRZA: [" + String.format("%2.2f", temperature.getTempreture()) + "-C]" + "\n" +
//                "CISNIENIE: [" + temperature.getPressure() + "-hpa]" + "\n" +
//                "PREDKOSC WIATRU: [" + String.format("%2.2f", wind.getSpeed()) + "-km/h]" + "\n" +
//                "DATA POMIARU: " + actualTime;
//    }
}
