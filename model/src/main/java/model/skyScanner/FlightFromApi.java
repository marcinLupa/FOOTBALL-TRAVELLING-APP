package model.skyScanner;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class FlightFromApi {

    @SerializedName("Places")
    private List<PlaceFromApi> placeFromApis = new ArrayList<>();
    @SerializedName("Quotes")
    private List<QuoteFromFlightApi> quoteFromFlightApis =new ArrayList<>();
    @SerializedName("Carriers")
    private List<CarrierFromApi> carrierFromApis = new ArrayList<>();

}
