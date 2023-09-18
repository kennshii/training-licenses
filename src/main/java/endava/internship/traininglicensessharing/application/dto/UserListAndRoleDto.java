package endava.internship.traininglicensessharing.application.dto;

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
@Schema(name = "roleDto")
public class UserListAndRoleDto {
    @Schema(example = "{1,2,3,4,5}",description = "Users' id")
    private Integer[] userIdList;

    @Schema(example = "3",description = "Role's id")
    private Integer roleId;
}
