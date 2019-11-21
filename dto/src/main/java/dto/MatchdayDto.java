package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.football.Competition;
import model.football.Match;

import javax.persistence.*;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static javax.persistence.CascadeType.PERSIST;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder


public class MatchdayDto {
    private Long matchdayId;

    private LeagueDto league;

    private List<MatchDto> matches;

    @Override
    public String toString() {
        return getMatches()
                .stream()
                .map(x -> "NR MECZU: " + x.getId() + " DATA : " + x.getDateOfMatch() +
                        "; KOLEJKA: " + x.getMatchday() + "; " +
                        x.getHomeTeam().getName() + " - " + x.getAwayTeam().getName() + "\n")
                .collect(joining());
    }

}
