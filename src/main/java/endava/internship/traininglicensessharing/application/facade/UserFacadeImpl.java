package endava.internship.traininglicensessharing.application.facade;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

import endava.internship.traininglicensessharing.application.dto.UserAdminDto;
import endava.internship.traininglicensessharing.application.mapper.UserMapper;
import endava.internship.traininglicensessharing.domain.entity.User;
import endava.internship.traininglicensessharing.domain.enums.UserRole;
import endava.internship.traininglicensessharing.domain.service.UserService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public List<UserAdminDto> getAllUsersAdmin() {
        List<User> users = userService.getAllUsers();

        return users.stream()
                .filter(user -> user.getLastActive() != null)
                .map(userMapper::userToUserAdminDto)
                .sorted(Comparator.comparingInt(dto -> UserRole.valueOf(dto.getRole().toUpperCase()).ordinal()))
                .toList();
    }

    @Override
    public boolean disableUser(Integer[] userIdList) {
        return userService.disableUser(userIdList);
    }

    @Override
    public boolean modifyUserRole(Integer[] userListId, Integer roleId) {
        return userService.modifyUserRole(userListId, roleId);
    }
}
