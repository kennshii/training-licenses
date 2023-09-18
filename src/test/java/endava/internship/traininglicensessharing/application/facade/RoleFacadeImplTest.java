package endava.internship.traininglicensessharing.application.facade;

import static endava.internship.traininglicensessharing.utils.RoleTestUtils.ROLE_DTO_LIST;
import static endava.internship.traininglicensessharing.utils.RoleTestUtils.ROLE_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import endava.internship.traininglicensessharing.application.mapper.RoleMapper;
import endava.internship.traininglicensessharing.domain.service.RoleService;

@ExtendWith(MockitoExtension.class)
class RoleFacadeImplTest {
    @Mock
    private RoleService roleService;
    @Mock
    private RoleMapper roleMapper;
    @InjectMocks
    private RoleFacadeImpl roleFacade;

    @Test
    void itShouldGetAllRoles() {
        when(roleMapper.roleToRoleDto(ROLE_LIST.get(1))).thenReturn(ROLE_DTO_LIST.get(0));
        when(roleMapper.roleToRoleDto(ROLE_LIST.get(2))).thenReturn(ROLE_DTO_LIST.get(1));

        when(roleService.getAllRoles()).thenReturn(ROLE_LIST);

        assertThat(roleFacade.getAllRoles()).isEqualTo(ROLE_DTO_LIST);
    }
}
