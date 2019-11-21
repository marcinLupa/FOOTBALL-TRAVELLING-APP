package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Set<ActualWeatherDto> actualWeathers;
}
