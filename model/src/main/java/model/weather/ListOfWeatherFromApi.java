package model.weather;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.skyScanner.CityFromApi;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ListOfWeatherFromApi {
    private CityFromApi city;
    @SerializedName( "list")
    private List<Weather> weatherList;

}
