package dto;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity
@Table(name="countries")
public class CountryDto {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "country")
    @EqualsAndHashCode.Exclude
    private Set<LeagueDto> leagueDtos;

}
