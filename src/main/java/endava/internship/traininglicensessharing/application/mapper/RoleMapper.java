package endava.internship.traininglicensessharing.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import endava.internship.traininglicensessharing.application.dto.RoleDto;
import endava.internship.traininglicensessharing.domain.entity.Role;

@Mapper
public interface RoleMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(expression = "java(role.getName().toString())", target = "name")
    @Mapping(target = "description", source = "description")
    RoleDto roleToRoleDto(Role role);
}
