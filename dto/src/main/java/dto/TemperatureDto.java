package dto;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity
@Table(name = "tamperatures")
public class TemperatureDto {

    @Id
    @GeneratedValue
    private Long id;

    private Double tempreture;
    private Double pressure;

    @OneToMany(mappedBy = "tempreture")
    @EqualsAndHashCode.Exclude
    private Set<ActualWeatherDto> actualWeathers;
}
