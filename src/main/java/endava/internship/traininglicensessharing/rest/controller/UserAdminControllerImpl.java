package endava.internship.traininglicensessharing.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import endava.internship.traininglicensessharing.application.dto.UserAdminDto;
import endava.internship.traininglicensessharing.application.dto.UserDisableDto;
import endava.internship.traininglicensessharing.application.dto.UserListAndRoleDto;
import endava.internship.traininglicensessharing.application.facade.UserFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "UserAdmin")
@CrossOrigin
class UserAdminControllerImpl implements UserAdminController {

    private final UserFacade userFacade;

    @Override
    @GetMapping
    public List<UserAdminDto> getAll() {
        return userFacade.getAllUsersAdmin();
    }

    @Override
    @PutMapping
    public ResponseEntity<Void> disableUser(@RequestBody UserDisableDto userDisableDto) {
        boolean response = userFacade.disableUser(userDisableDto.getUserIdList());

        return response ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    @PutMapping("/roles")
    public ResponseEntity<Void> modifyUserRole(@RequestBody UserListAndRoleDto userListAndRoleDto) {
        boolean response = userFacade.modifyUserRole(userListAndRoleDto.getUserIdList(), userListAndRoleDto.getRoleId());

        return response ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
