package endava.internship.traininglicensessharing.application.dto;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
@Schema(name = "licenseCost")
public class LicenseCostDto {

    @Schema(example = "[{\"year\": 2023,\"month\": 6,\"cost\": \"1122\"}]",
            description = "List of monthly costs")
    private List<MonthDto> costsReport;

    @Schema(example = "5432",
            description = "Cost for the current year, presented in digits")
    private BigDecimal costsForCurrentYear;

    @Schema(example = "150",
            description = "Difference of total costs between current and previous years (can be positive/negative)")
    private BigDecimal deltaCosts;

    @Schema(example = "450",
            description = "Total number of Software licenses")
    private Long numberOfSoftware;

    @Schema(example = "150",
            description = "Difference of Software licenses between current and previous years (can be positive/negative)")
    private Long deltaSoftware;

    @Schema(example = "500",
            description = "Total number of Training licenses")
    private Long numberOfTrainings;

    @Schema(example = "200",
            description = "Difference of Training licenses between current and previous years (can be positive/negative)")
    private Long deltaTrainings;
}