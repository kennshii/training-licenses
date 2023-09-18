package endava.internship.traininglicensessharing.domain.service;

import java.util.List;

import endava.internship.traininglicensessharing.domain.entity.User;

public interface UserService {

    List<User> getAllUsers();

    boolean disableUser(Integer[] userIdList);

    boolean modifyUserRole(Integer[] userListId, Integer roleId);

}
