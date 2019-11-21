package model.skyScanner;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

//@Entity
public class CarrierFromApi {
//    @Id
//    @GeneratedValue
//    private Long idFromDb;
    @SerializedName("CarrierId")
    private Long carrierId;
    @SerializedName("Name")
    private String carrierName;

}