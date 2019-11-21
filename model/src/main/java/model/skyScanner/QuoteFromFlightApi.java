package model.skyScanner;

import com.google.gson.annotations.SerializedName;
import lombok.*;



@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class QuoteFromFlightApi {

    @SerializedName("MinPrice")
    private Double minPrice;
    @SerializedName("OutboundLeg")
    private OtherDataFromQuoteApi otherDataFromQuoteApi;


}
