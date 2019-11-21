package model.football;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static javax.persistence.CascadeType.PERSIST;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

//@Entity
public class Matchday {

//    @Id
//    @GeneratedValue
//    private Long matchdayId;
//    @ManyToOne(cascade = PERSIST)
//    @JoinColumn(name = "competition_id", referencedColumnName = "idFromDb")
    private Competition competition;
    //  @OneToMany(cascade = PERSIST,mappedBy = "matchday")
//    @Transient
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
