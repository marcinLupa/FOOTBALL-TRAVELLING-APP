package dto;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

@Entity
@Table(name="matches")
public class MatchDto {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDate dateOfMatch;
    private Integer matchday;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="home_team_id")
    private TeamDto homeTeam;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="away_team_id")
    private TeamDto awayTeam;

    @OneToMany(mappedBy = "match")
    @EqualsAndHashCode.Exclude
    private Set<GameDto> gameDtoSet;



}
