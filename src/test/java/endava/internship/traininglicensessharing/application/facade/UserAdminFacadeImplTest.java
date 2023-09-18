package endava.internship.traininglicensessharing.application.facade;


import static endava.internship.traininglicensessharing.utils.UserTestUtils.USER;
import static endava.internship.traininglicensessharing.utils.UserTestUtils.USER_ADMIN_DTO;
import static endava.internship.traininglicensessharing.utils.UserTestUtils.USER_DISABLE_DTO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import endava.internship.traininglicensessharing.application.dto.UserAdminDto;
import endava.internship.traininglicensessharing.application.mapper.UserMapper;
import endava.internship.traininglicensessharing.domain.entity.User;
import endava.internship.traininglicensessharing.domain.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserAdminFacadeImplTest {

    @InjectMocks
    UserFacadeImpl userFacade;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Test
    void itShouldGetAllUsers() {
        final List<User> users = List.of(USER);

        when(userService.getAllUsers()).thenReturn(users);
        when(userMapper.userToUserAdminDto(USER)).thenReturn(USER_ADMIN_DTO);

        final List<UserAdminDto> usersDto = userFacade.getAllUsersAdmin();

        assertThat(usersDto.size()).isEqualTo(1);
        assertThat(usersDto.get(0)).isEqualTo(USER_ADMIN_DTO);
    }

    @Test
    void itShouldReturnTrueWhenDisableExistentUser() {
        when(userService.disableUser(USER_DISABLE_DTO.getUserIdList()))
                .thenReturn(true);

        assertThat(userService.disableUser(USER_DISABLE_DTO.getUserIdList())).isTrue();
    }

    @Test
    void itShouldReturnFalseWhenDisableNonexistentUser() {
        when(userService.disableUser(USER_DISABLE_DTO.getUserIdList())).thenReturn(false);

        assertThat(userService.disableUser(USER_DISABLE_DTO.getUserIdList())).isFalse();
    }

    @Test
    void itShouldReturnTrueWhenModifyRoleOfExistentUser() {
        when(userService.modifyUserRole(any(), any())).thenReturn(true);

        assertThat(userService.modifyUserRole(new Integer[]{1, 2, 3}, 2)).isTrue();
    }

    @Test
    void itShouldReturnFalseWhenModifyRoleOfNonexistentUser() {
        when(userService.modifyUserRole(any(), any())).thenReturn(false);

        assertThat(userService.modifyUserRole(new Integer[]{1, 2, 3}, 2)).isFalse();
    }

}
