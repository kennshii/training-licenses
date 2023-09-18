package endava.internship.traininglicensessharing.application.dto;

import java.math.BigDecimal;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Schema(name = "licenseCostPerMonth")
public class MonthDto implements Comparable<MonthDto> {

    @Schema(example = "2023",
            description = "Year for which cost was calculated")
    private Integer year;

    @Schema(example = "6",
            description = "Month for which cost was calculated")
    private Integer month;

    @Schema(example = "99",
            description = "Total cost for license per month")
    private BigDecimal cost;

    @Override
    public int compareTo(MonthDto o) {
        if (this.getYear() > o.getYear()) {
            return 1;
        } else if (this.getYear().equals(o.getYear())) {
            return this.getMonth().compareTo(o.getMonth());
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonthDto monthDTO = (MonthDto) o;
        return Objects.equals(year, monthDTO.year) && Objects.equals(month, monthDTO.month);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month);
    }
}