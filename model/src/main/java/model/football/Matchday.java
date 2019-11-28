package model.football;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.joining;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class Matchday {

    private Competition competition;

    private List<Match> matches;

    @Override
    public String toString() {
        return getMatches()
                .stream()
                .map(x -> "NR MECZU: " + x.getId() + " DATA : " + x.getUtcDate().replaceAll("T", " ").replaceAll("Z", "") +
                        "; KOLEJKA: " + x.getMatchday() + "; " +
                        x.getHomeTeam().getName() + " - " + x.getAwayTeam().getName() + "\n")
                .collect(joining());
    }
}
