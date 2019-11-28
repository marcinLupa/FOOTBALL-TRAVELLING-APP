package dto;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity
public class AirlineDto {
    @Id
    @GeneratedValue
    private Long id;
    private Long AirlineId;
    private String carrierName;

    @OneToMany(mappedBy = "airline")
    @EqualsAndHashCode.Exclude
    private Set<FlightDto> flightDtos;

    @Override
    public String toString() {
        return "\n" + " SKY SCANNER AIRLINE ID: " + getAirlineId() + "\n" +
                " AIERLINE NAME: '" + getAirlineId() + "\n";
    }
}

