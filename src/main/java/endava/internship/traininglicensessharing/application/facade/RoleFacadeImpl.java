package endava.internship.traininglicensessharing.application.facade;

import java.util.List;

import org.springframework.stereotype.Component;

import endava.internship.traininglicensessharing.application.dto.RoleDto;
import endava.internship.traininglicensessharing.application.mapper.RoleMapper;
import endava.internship.traininglicensessharing.domain.enums.UserRole;
import endava.internship.traininglicensessharing.domain.service.RoleService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RoleFacadeImpl implements RoleFacade {
    private final RoleService roleService;
    private final RoleMapper roleMapper;

    @Override
    public List<RoleDto> getAllRoles() {
        return roleService.getAllRoles()
                .stream()
                .filter(role -> !role.getName().equals(UserRole.ADMIN))
                .map(roleMapper::roleToRoleDto)
                .toList();
    }
}
