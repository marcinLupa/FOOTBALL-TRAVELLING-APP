package model.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class Wind {

    private Long id;
    private final double msToKmH = 3.6;

    private Double speed;

    public Wind(Double speed) {

        setSpeed(speed);
    }

    public void setSpeed(Double speed) {

        this.speed = speed * msToKmH;
    }

}
