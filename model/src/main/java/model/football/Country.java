package model.football;

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

//@Entity
public class Country {
//    @Id
//    @GeneratedValue
    //private long idFromDb;
    private String name;

//    @OneToMany(mappedBy = "area")
 //   private Set<Competition> competitionSet;
}
