package model.weather;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Weather {

    public Temperature main;
    private Wind wind;
    @SerializedName("dt_txt")
    private String dateOfMeasurement;
}
