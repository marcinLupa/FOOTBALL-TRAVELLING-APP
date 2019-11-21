package model.football;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.CascadeType.PERSIST;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

//@Entity
public class Competition {
//    @Id
//    @GeneratedValue
//    private Long idFromDb;
    @SerializedName("id")
    private Long competitionId;
    private String code;
    @SerializedName("name")
    private String competitionName;
    @ManyToOne(cascade=PERSIST)
    private Country area;

    @Override
    public String toString() {
        return "NUMER LIGI: " + getCompetitionId() + ";" +
                "NAZWA LIGI: " + getCompetitionName();
    }
}
