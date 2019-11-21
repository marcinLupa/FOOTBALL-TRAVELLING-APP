package model.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class PopularityAndTicketPrice {
    @Id
    @GeneratedValue
    private Long id;

    private Integer count;
    private String cityName;
    private BigDecimal avgTicketPrice;
}
