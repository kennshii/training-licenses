package endava.internship.traininglicensessharing.domain.service;

import static endava.internship.traininglicensessharing.utils.RoleTestUtils.ROLE_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import endava.internship.traininglicensessharing.domain.repository.RoleRepository;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void itShouldReturnAllRoles() {
        when(roleRepository.findAll()).thenReturn(ROLE_LIST);

        assertThat(roleService.getAllRoles()).isEqualTo(ROLE_LIST);
    }
}
