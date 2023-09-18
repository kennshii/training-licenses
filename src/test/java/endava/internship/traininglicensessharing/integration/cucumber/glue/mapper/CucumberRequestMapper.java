package endava.internship.traininglicensessharing.integration.cucumber.glue.mapper;

import endava.internship.traininglicensessharing.domain.entity.Request;
import endava.internship.traininglicensessharing.integration.cucumber.glue.context.ScenarioContext;
import endava.internship.traininglicensessharing.integration.cucumber.glue.dto.CucumberRequestDto;

public class CucumberRequestMapper {

    public static Request cucumberRequestDtoToRequest(CucumberRequestDto cucumberRequestDto, ScenarioContext scenarioContext) {
        return Request.builder()
                .license(scenarioContext.getTheLicenseByName(cucumberRequestDto.getLicense()))
                .user(scenarioContext.getTheUserByFirstNameAndLastName(cucumberRequestDto.getFirstName(), cucumberRequestDto.getLastName()))
                .status(cucumberRequestDto.getStatus())
                .requestedOn(cucumberRequestDto.getRequestedOn())
                .startOfUse(cucumberRequestDto.getStartOfUse())
                .build();
    }



}
