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
@Table(name="winds")
public class WindDto {

    @Id
    @GeneratedValue
    private Long id;

    private Double speed;

    @OneToMany(mappedBy = "wind")
    private Set<ActualWeatherDto> actualWeathers;

}
