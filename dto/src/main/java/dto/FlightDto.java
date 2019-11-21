package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
//
@Entity
@Table(name="flights")
public class FlightDto {

    @Id
    @GeneratedValue
   private Long id;

    @ManyToOne(cascade = PERSIST)
    @JoinColumn(name= "airline_id")
    private AirlineDto airline;

    private LocalDate departureDate;

    @ManyToOne(cascade = PERSIST)
    @JoinColumn(name= "arrivalCity_id")
    private CityDto arrivalCity;

    @ManyToOne(cascade = PERSIST)
    @JoinColumn(name= "departureCity_id")
    private CityDto departureCity;

    private BigDecimal ticketPrice;

//    @OneToOne(mappedBy = "chosenFlight")
//    private SearchDto searchDtoOne;
//
//    @OneToOne(mappedBy = "comebackFlight")
//    private SearchDto searchDtoSecond;

    @Override
    public String toString() {
        return "NR LOTU: " + getId() + ", "
                + " LINIA LOTNICZA: " + getAirline().getCarrierName() + ", "
                + " DATA: " + getDepartureDate() + ", "
                + " MIEJSCE PRZYLOTU: " + getArrivalCity().getCityName() + ", "
                + " MIEJSCE ODLOTU: " + getDepartureCity().getCityName() + ", "
                + " CENA BILETU: " + getTicketPrice() + ", " + "\n";
    }

}

