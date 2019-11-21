package dto;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import model.football.Country;

import javax.persistence.*;

import java.util.Set;

import static javax.persistence.CascadeType.PERSIST;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity
@Table(name="leagues")
public class LeagueDto {

    @Id
    @GeneratedValue
    private Long id;
    private Long leagueId;
    private String leagueCode;
    private String leagueName;

    @ManyToOne(cascade = PERSIST)
    @JoinColumn(name="country_id")
    private CountryDto country;

    @OneToMany(mappedBy = "league")
    @EqualsAndHashCode.Exclude
    private Set<GameDto> gameDtos;

    @Override
    public String toString() {
        return "NUMER LIGI: " + getLeagueId() + ";" +
                "NAZWA LIGI: " + getLeagueName();
    }
}
