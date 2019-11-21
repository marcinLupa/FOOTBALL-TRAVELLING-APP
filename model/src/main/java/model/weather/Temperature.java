package model.weather;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.text.DecimalFormat;

@AllArgsConstructor
@Data
public class Temperature {

    private Long id;
    @SerializedName("temp")
    private Double tempreture;
    private Double pressure;


    private Temperature(TemperatureBuilder builder) {
        this.id = builder.id;
        this.tempreture = builder.tempreture;
        this.pressure = builder.pressure;
    }


    public static TemperatureBuilder builder() {

        return new TemperatureBuilder();
    }

    public static class TemperatureBuilder {

        private Long id;
        private Double tempreture;
        private Double pressure;


        private static final Double KELVIN = 273.15;

        public TemperatureBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TemperatureBuilder tempreture(Double tempreture) {
            DecimalFormat df2 = new DecimalFormat("#.##");

            Double doubleTemperature =Double.parseDouble( df2.format(tempreture-KELVIN).replace(",","."));

            this.tempreture =doubleTemperature ;
            return this;
        }

        public TemperatureBuilder pressure(Double pressure) {
            this.pressure = pressure;
            return this;
        }


        public Temperature builder() {

            return new Temperature(this);
        }
    }
}