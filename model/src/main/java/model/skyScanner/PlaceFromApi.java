package model.skyScanner;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class PlaceFromApi {

    @SerializedName("PlaceId")
    private Long placeId;
    @SerializedName("CityName")
    private String cityName;
    @SerializedName("Name")
    private String airportName;

}
