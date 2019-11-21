package model.football;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.CascadeType.PERSIST;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class Match {

   private Long id;
    private String utcDate;
    private Integer matchday;
    private Team homeTeam;
    private Team awayTeam;
//    @ManyToOne(cascade = PERSIST)
//    @JoinColumn(name = "matchday_id")
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude



}
