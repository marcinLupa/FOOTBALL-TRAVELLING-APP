package model.skyScanner;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import model.weather.ActualWeather;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

//@Entity
public class CityFromApi {


//    @Id
//    @GeneratedValue
 //   private Long idFromDb;

    private Long id;
    @SerializedName("PlaceId")
    private String airportSkyScannerId;
    @SerializedName("airportName")
    private String airportName;
    @SerializedName("CityId")
    private String citySkyScannerId;
    @SerializedName("CountryName")
    private String country;
    @SerializedName(value = "PlaceName", alternate = "name")
    private String nameOfCity;

//    @OneToMany(mappedBy = "city")
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
    private List<ActualWeather> actualWeathers;

    public void addActualWeather(ActualWeather... aw) {
        if (aw != null) {
            for (ActualWeather a : aw) {
                a.setCity(this);
                actualWeathers.add(a);
            }
        }
    }
}
