package dto;

import lombok.*;

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
    @EqualsAndHashCode.Exclude
    private Set<ActualWeatherDto> actualWeathers;

}
