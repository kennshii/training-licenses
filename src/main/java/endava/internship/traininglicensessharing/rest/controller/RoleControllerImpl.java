package endava.internship.traininglicensessharing.rest.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import endava.internship.traininglicensessharing.application.dto.RoleDto;
import endava.internship.traininglicensessharing.application.facade.RoleFacade;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
class RoleControllerImpl implements RoleController {
    private final RoleFacade roleFacade;

    @Override
    @GetMapping("/roles")
    public List<RoleDto> getAllRoles() {
        return roleFacade.getAllRoles();
    }
}
