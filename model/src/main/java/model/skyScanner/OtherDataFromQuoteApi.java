package model.skyScanner;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder


public class OtherDataFromQuoteApi {
    @SerializedName("CarrierIds")
    private List<Long> carrierIds;
    @SerializedName("OriginId")
    private Long originId;
    @SerializedName("DestinationId")
    private Long destinationId;
    @SerializedName("DepartureDate")
    private String departureDate;


}