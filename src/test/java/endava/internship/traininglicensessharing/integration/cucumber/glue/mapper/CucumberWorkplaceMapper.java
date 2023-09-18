package endava.internship.traininglicensessharing.integration.cucumber.glue.mapper;

import endava.internship.traininglicensessharing.domain.entity.Workplace;
import endava.internship.traininglicensessharing.integration.cucumber.glue.context.ScenarioContext;
import endava.internship.traininglicensessharing.integration.cucumber.glue.dto.CucumberWorkplaceDto;

public class CucumberWorkplaceMapper {

    public static Workplace cucumberWorkplaceDtoToWorkplace(CucumberWorkplaceDto cucumberWorkplaceDto, ScenarioContext scenarioContext) {
        return Workplace.builder()
                .discipline(scenarioContext.getTheDisciplineByName(cucumberWorkplaceDto.getDiscipline()))
                .position(scenarioContext.getThePositionByName(cucumberWorkplaceDto.getPosition()))
                .deliveryUnit(scenarioContext.getDeliveryUnitByName(cucumberWorkplaceDto.getDeliveryUnit()))
                .build();
    }

}
