package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity
@Table(name="weathers")

public class ActualWeatherDto {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "tempreture_id")
    private TemperatureDto tempreture;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "wind_id")
    private WindDto wind;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "city_id")
    private CityDto city;

    private LocalDate actualTime;

    @Override
    public String toString() {
        return "TEMPERTURA POWIETRZA: [" + String.format("%2.2f", tempreture.getTempreture()) + "-C]" + "\n" +
                "CISNIENIE: [" + tempreture.getPressure() + "-hpa]" + "\n" +
                "PREDKOSC WIATRU: [" + String.format("%2.2f", wind.getSpeed()) + "-km/h]" + "\n" +
                "DATA POMIARU: " + actualTime;
    }
}
