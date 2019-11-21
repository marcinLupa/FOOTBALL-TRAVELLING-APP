package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Set<MatchDto> matchDtosHomeTeam;
    @OneToMany(mappedBy = "awayTeam")
    private Set<MatchDto> matchDtosAwayTeam;
}
