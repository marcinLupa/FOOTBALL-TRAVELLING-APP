package model.skyScanner;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class CityListFromApi {
    @SerializedName("Places")
    private List<CityFromApi> cities;
}
