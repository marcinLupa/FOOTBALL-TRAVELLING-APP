package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

import static java.util.stream.Collectors.joining;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

@Entity
@Table(name="games")
public class GameDto {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="match_id")
    private MatchDto match;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="league_id")
    private LeagueDto league;

    @OneToMany(mappedBy = "chosenMatch")
    private Set<SearchDto> searchDtos;

    @Override
    public String toString() {
        return " DATA : " + match.getDateOfMatch() +
                        "; KOLEJKA: " + match.getMatchday() + "; " +
                        match.getHomeTeam().getName() + " - " + match.getAwayTeam().getName() + "\n";
    }

}
