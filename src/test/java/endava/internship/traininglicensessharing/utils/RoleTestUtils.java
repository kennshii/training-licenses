package endava.internship.traininglicensessharing.utils;

import java.util.List;

import endava.internship.traininglicensessharing.application.dto.RoleDto;
import endava.internship.traininglicensessharing.domain.entity.Role;
import endava.internship.traininglicensessharing.domain.enums.UserRole;

public class RoleTestUtils {
    public static final Role ROLE = Role.builder()
            .name(UserRole.ADMIN)
            .build();
    public static final RoleDto ROLE_DTO = RoleDto.builder()
            .name(ROLE.getName().toString())
            .build();
    public static final List<Role> ROLE_LIST = List.of(
            ROLE,
            Role.builder()
                    .name(UserRole.REVIEWER)
                    .description("Reviewer approves or rejects requests for access to learning resources.")
                    .build(),
            Role.builder()
                    .name(UserRole.USER)
                    .description("User can request access to learning resources.")
                    .build()
    );
    public static final List<RoleDto> ROLE_DTO_LIST = List.of(
            RoleDto.builder()
                    .name(ROLE_LIST.get(1).getName().toString())
                    .description(ROLE_LIST.get(1).getDescription())
                    .build(),
            RoleDto.builder()
                    .name(ROLE_LIST.get(2).getName().toString())
                    .description(ROLE_LIST.get(2).getDescription())
                    .build()
    );
}
