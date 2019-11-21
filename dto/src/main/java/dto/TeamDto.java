package dto;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity
@Table(name = "teams")
public class TeamDto {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "homeTeam")
    @EqualsAndHashCode.Exclude
    private Set<MatchDto> matchDtosHomeTeam;
    @OneToMany(mappedBy = "awayTeam")
    @EqualsAndHashCode.Exclude
    private Set<MatchDto> matchDtosAwayTeam;
}
