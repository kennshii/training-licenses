package endava.internship.traininglicensessharing.domain.service;


import static endava.internship.traininglicensessharing.utils.RoleTestUtils.ROLE;
import static endava.internship.traininglicensessharing.utils.RoleTestUtils.ROLE_LIST;
import static endava.internship.traininglicensessharing.utils.UserTestUtils.USER_LIST;
import static endava.internship.traininglicensessharing.utils.UserTestUtils.WORKPLACE_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import endava.internship.traininglicensessharing.domain.entity.User;
import endava.internship.traininglicensessharing.domain.repository.RoleRepository;
import endava.internship.traininglicensessharing.domain.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void itShouldReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(USER_LIST);

        assertThat(userService.getAllUsers())
                .isEqualTo(USER_LIST)
                .isSameAs(USER_LIST);
    }

    @Test
    void itShouldReturnTrueWhenDisableExistentUser() {
        User user1 = User.builder()
                .userId(1)
                .firstName("FirstName1")
                .lastName("LastName1")
                .email("FirstName1.LastName1@endava.com")
                .roles(Set.of(ROLE_LIST.get(0), ROLE_LIST.get(1)))
                .isActive(true)
                .lastActive(LocalDate.now().minusDays(10))
                .registrationDate(LocalDate.now().minusDays(15))
                .workplace(WORKPLACE_LIST.get(0))
                .build();
        User user2 = User.builder()
                .userId(2)
                .firstName("FirstName2")
                .lastName("LastName2")
                .email("FirstName2.LastName2@endava.com")
                .roles(Set.of(ROLE_LIST.get(0), ROLE_LIST.get(1)))
                .isActive(true)
                .lastActive(LocalDate.now().minusDays(10))
                .registrationDate(LocalDate.now().minusDays(15))
                .workplace(WORKPLACE_LIST.get(1))
                .build();

        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user1));
        when(userRepository.findById(2)).thenReturn(Optional.ofNullable(user2));

        assertThat(userService.disableUser(new Integer[]{1, 2})).isTrue();
    }

    @Test
    void itShouldReturnFalseWhenDisableNonexistentUser() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThat(userService.disableUser((new Integer[]{-1, 1}))).isFalse();
    }

    @Test
    void itShouldReturnTrueWhenModifyRoleOfExistentUserAndRole() {
        when(roleRepository.findById(anyInt())).thenReturn(Optional.of(ROLE_LIST.get(2)));

        when(roleRepository.findAll()).thenReturn(new ArrayList<>(ROLE_LIST.subList(1, 3)));

        when(userRepository.findById(1)).thenReturn(Optional.of(USER_LIST.get(0)));
        when(userRepository.findById(2)).thenReturn(Optional.of(USER_LIST.get(1)));

        assertThat(userService.modifyUserRole(new Integer[]{1, 2}, 2)).isTrue();
    }

    @Test
    void itShouldReturnFalseWhenModifyRoleOfNonexistentUserAndExistentRole() {
        when(roleRepository.findById(2)).thenReturn(Optional.of(ROLE_LIST.get(2)));

        when(roleRepository.findAll()).thenReturn(new ArrayList<>(ROLE_LIST.subList(1, 3)));

        when(userRepository.findById(1)).thenReturn(Optional.of(USER_LIST.get(0)));
        when(userRepository.findById(2)).thenReturn(Optional.empty());

        assertThat(userService.modifyUserRole(new Integer[]{1, 2}, 2)).isFalse();
    }

    @Test
    void itShouldReturnFalseWhenNonexistentRole() {
        when(roleRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThat(userService.modifyUserRole(new Integer[]{1, 2}, 2)).isFalse();
    }

    @Test
    void itShouldReturnFalseWhenTryToModifyRoleToAdmin() {
        when(roleRepository.findById(anyInt())).thenReturn(Optional.of(ROLE));

        assertThat(userService.modifyUserRole(new Integer[]{1, 2}, 1)).isFalse();
    }
}
