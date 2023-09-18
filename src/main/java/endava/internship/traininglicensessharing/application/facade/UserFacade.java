package endava.internship.traininglicensessharing.application.facade;

import java.util.List;

import endava.internship.traininglicensessharing.application.dto.UserAdminDto;

public interface UserFacade {

    List<UserAdminDto> getAllUsersAdmin();

    boolean disableUser(Integer[] userIdList);

    boolean modifyUserRole(Integer[] userListId, Integer roleId);

}
