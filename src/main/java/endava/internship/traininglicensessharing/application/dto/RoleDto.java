package endava.internship.traininglicensessharing.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Schema(name = "role")
public class RoleDto {
    @EqualsAndHashCode.Exclude
    @Schema(example = "1",description = "Role's id")
    private Integer id;

    @Schema(example = "Admin",description = "Role's name'")
    private String name;

    @Schema(example = "Reviewer approves or rejects requests for access to learning resources.",
    description = "Description for every role")
    private String description;
}
