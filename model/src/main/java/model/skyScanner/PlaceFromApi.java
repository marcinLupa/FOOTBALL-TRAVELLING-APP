package model.skyScanner;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

//@Entity
public class PlaceFromApi {
//    @Id
//    @GeneratedValue
//    private Long placeIdtoDb;
    @SerializedName("PlaceId")
    private Long placeId;
    @SerializedName("CityName")
    private String cityName;
    @SerializedName("Name")
    private String airportName;

}
