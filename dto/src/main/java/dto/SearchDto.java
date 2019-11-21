package dto;

import lombok.*;
import model.football.Matchday;
import model.weather.ActualWeather;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

@Entity
@Table(name="search")
public class SearchDto {
    @Id
    @GeneratedValue
    private Long id;

  //  private MatchdayDto chosenMatch;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="game_id")
    private GameDto chosenMatch;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="arrival_flight_id")
    private FlightDto chosenFlight;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="comeback_flight_id")
    private FlightDto comebackFlight;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="weather_id")
    private ActualWeatherDto actualWeather;

    public SearchDto(GameDto chosenMatch, FlightDto chosenFlight, FlightDto comebackFlight, ActualWeatherDto actualWeather) {
        this.chosenMatch = chosenMatch;
        this.chosenFlight = chosenFlight;
        this.comebackFlight = comebackFlight;
        this.actualWeather = actualWeather;
    }


    private String combackFlightString(FlightDto comebackFlight) {
        if (comebackFlight != null) {
            return "WROCISZ SAMOLOTEM: " + "\n"
                    + comebackFlight + "\n";
        } else {
            return "";
        }
    }

    @Override
    public String toString() {

        return
                "------------------------" + "\n" +
                        "TWOJ WYBRANY MECZ NA KTORY CHCESZ LECIEC TO: " + "\n" +
                        chosenMatch + "\n" +
                        "POLECISZ SAMOLOTEM: " + "\n" +
                        chosenFlight + "\n" +
                        combackFlightString(comebackFlight) + "\n" +
                        "AKTUALNA POGODA W " + chosenFlight.getArrivalCity().getCityName().toUpperCase() + "\n" +
                        actualWeather + "\n";
    }
}
