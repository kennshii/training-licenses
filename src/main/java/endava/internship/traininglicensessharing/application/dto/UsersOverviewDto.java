package endava.internship.traininglicensessharing.application.dto;

import java.util.List;

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
@Builder
@Schema(name = "usersOverview")
@ToString
public class UsersOverviewDto {

    @Schema(example = "12813",
            description = "Total number of users that accessed Training licenses site")
    private Long totalUsers;

    @Schema(example = "230",
            description = "Number of new users that accessed the site for the first time during this month")
    private Long deltaUsers;

    @Schema(example = "150",
            description = "Total number of disciplines")
    private Long totalDisciplines;

    @Schema(example = "{\"id\": \"221\", \"name\": \"Development\", \"value\": \"9\"}",
            description = "Total number of users per discipline")
    private List<DisciplineValueDto> numberOfUsersPerDiscipline;
}