package dto;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity
@Table(name="cities")
public class CityDto {

    @Id
    @GeneratedValue
    private Long id;

    private Long cityId;

    private String cityName;

    private String airportSkyScannerid;

    private String airportName;

    @OneToMany(mappedBy = "city")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ActualWeatherDto> actualWeathers;

    @OneToMany(mappedBy = "arrivalCity")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<FlightDto> flightDtosArrival;

    @OneToMany(mappedBy = "departureCity")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<FlightDto> flightDtosDeparture;

    @Override
    public String toString() {
        return "\n" + " SKY SCANNER CITY ID: " + cityId +"\n"+
                " CITY NAME: '" + cityName  +"\n"+
                " AIRPORT NAME: " + airportName  +"\n";
    }
}
