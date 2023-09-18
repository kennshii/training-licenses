package endava.internship.traininglicensessharing.integration.cucumber.glue.mapper;

import endava.internship.traininglicensessharing.domain.entity.User;
import endava.internship.traininglicensessharing.integration.cucumber.glue.context.ScenarioContext;
import endava.internship.traininglicensessharing.integration.cucumber.glue.dto.CucumberUserDto;
import endava.internship.traininglicensessharing.integration.cucumber.glue.dto.CucumberUserRoleDto;
import endava.internship.traininglicensessharing.integration.cucumber.glue.dto.CucumberWorkplaceDto;

public class CucumberUserMapper {

    public static User cucumberUserDtoToUser(CucumberUserDto cucumberUserDto, ScenarioContext scenarioContext) {
        CucumberWorkplaceDto workplaceDto = CucumberWorkplaceDto.builder()
                .deliveryUnit(cucumberUserDto.getDeliveryUnit())
                .discipline(cucumberUserDto.getDiscipline())
                .position(cucumberUserDto.getPosition())
                .build();

        return User.builder()
                .firstName(cucumberUserDto.getFirstName())
                .lastName(cucumberUserDto.getLastName())
                .email(cucumberUserDto.getEmail())
                .workplace(CucumberWorkplaceMapper.cucumberWorkplaceDtoToWorkplace(workplaceDto, scenarioContext))
                .registrationDate(cucumberUserDto.getRegistrationDate())
                .lastActive(cucumberUserDto.getLastActive())
                .isActive(Boolean.parseBoolean(cucumberUserDto.getIsActive()))
                .build();
    }

    public static User cucumberUserRoleDtoToUser(CucumberUserRoleDto cucumberUserRoleDto, ScenarioContext scenarioContext) {
        CucumberWorkplaceDto workplaceDto = CucumberWorkplaceDto.builder()
                .deliveryUnit(cucumberUserRoleDto.getDeliveryUnit())
                .discipline(cucumberUserRoleDto.getDiscipline())
                .position(cucumberUserRoleDto.getPosition())
                .build();

        return User.builder()
                .firstName(cucumberUserRoleDto.getFirstName())
                .lastName(cucumberUserRoleDto.getLastName())
                .email(cucumberUserRoleDto.getEmail())
                .roles(scenarioContext.returnUserRolesSetByRole(cucumberUserRoleDto.getRole()))
                .workplace(CucumberWorkplaceMapper.cucumberWorkplaceDtoToWorkplace(workplaceDto, scenarioContext))
                .registrationDate(cucumberUserRoleDto.getRegistrationDate())
                .lastActive(cucumberUserRoleDto.getLastActive())
                .isActive(Boolean.parseBoolean(cucumberUserRoleDto.getIsActive()))
                .build();
    }

}
