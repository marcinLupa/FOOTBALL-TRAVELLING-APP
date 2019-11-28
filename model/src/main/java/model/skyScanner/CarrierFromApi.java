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

public class CarrierFromApi {

    @SerializedName("CarrierId")
    private Long carrierId;
    @SerializedName("Name")
    private String carrierName;

}