package endava.internship.traininglicensessharing.application.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "averageCosts")
public class AverageCostsDto {

    @Schema(example = "55",
            description = "Average license cost per user")
    private Long averageCostPerUser;

    @Schema(example = "{\"id\": \"221\", \"name\": \"Development\", \"value\": \"3280\"}",
            description = "Map of departments with their average costs")
    private List<DisciplineValueDto> averageCostPerDepartmentsMap;
}