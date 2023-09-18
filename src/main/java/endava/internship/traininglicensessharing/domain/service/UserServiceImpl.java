package endava.internship.traininglicensessharing.domain.service;


import static endava.internship.traininglicensessharing.domain.cache.CacheContext.USERS_CACHE;
import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import endava.internship.traininglicensessharing.domain.entity.Role;
import endava.internship.traininglicensessharing.domain.entity.User;
import endava.internship.traininglicensessharing.domain.enums.UserRole;
import endava.internship.traininglicensessharing.domain.repository.RoleRepository;
import endava.internship.traininglicensessharing.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Cacheable(value = USERS_CACHE)
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @CacheEvict(value = USERS_CACHE, allEntries = true)
    @Override
    @Transactional
    public boolean disableUser(Integer[] userIdList) {
        List<User> userList = findAllUsersById(userIdList);

        if (userList.contains(null))
            return false;
        userList.forEach(user -> user.setIsActive(false));

        return true;
    }

    @CacheEvict(value = USERS_CACHE, allEntries = true)
    @Override
    @Transactional
    public boolean modifyUserRole(Integer[] userListId, Integer roleId) {
        Role requiredRole = roleRepository.findById(roleId).orElse(null);

        if (isNull(requiredRole) || requiredRole.getName() == UserRole.ADMIN)
            return false;

        List<Role> roles = getAllNecessaryRoleToAdd(requiredRole);
        List<User> users = findAllUsersById(userListId);

        if (users.contains(null))
            return false;

        users.forEach(user -> {
                    user.getRoles().clear();
                    user.getRoles().addAll(roles);
                }
        );

        return true;
    }

    private List<Role> getAllNecessaryRoleToAdd(Role requiredRole) {
        List<Role> roleList = roleRepository.findAll();

        return roleList.stream()
                .filter(role ->
                        role.getName().ordinal() >= requiredRole.getName().ordinal())
                .toList();

    }

    private List<User> findAllUsersById(Integer[] userListId) {
        List<User> users = new ArrayList<>();

        List.of(userListId)
                .forEach(id -> users.add(userRepository.findById(id).orElse(null)));

        return users;
    }

}
