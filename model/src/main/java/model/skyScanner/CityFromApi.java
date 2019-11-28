package model.skyScanner;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.weather.ActualWeather;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class CityFromApi {


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
