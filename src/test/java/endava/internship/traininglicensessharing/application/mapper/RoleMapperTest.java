package endava.internship.traininglicensessharing.application.mapper;

import static endava.internship.traininglicensessharing.utils.RoleTestUtils.ROLE;
import static endava.internship.traininglicensessharing.utils.RoleTestUtils.ROLE_DTO;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class RoleMapperTest {
    private final RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);

    @Test
    void itShouldMapRoleToRoleDto() {
        assertThat(roleMapper.roleToRoleDto(ROLE)).isEqualTo(ROLE_DTO);
    }
}
