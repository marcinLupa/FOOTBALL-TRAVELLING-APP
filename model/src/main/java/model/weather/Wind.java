package model.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

//@Entity
public class Wind {
//    @Id
//    @GeneratedValue
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
