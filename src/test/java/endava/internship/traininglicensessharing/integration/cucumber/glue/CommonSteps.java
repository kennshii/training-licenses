package endava.internship.traininglicensessharing.integration.cucumber.glue;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;

import endava.internship.traininglicensessharing.domain.entity.DeliveryUnit;
import endava.internship.traininglicensessharing.domain.entity.Discipline;
import endava.internship.traininglicensessharing.domain.entity.License;
import endava.internship.traininglicensessharing.domain.entity.Position;
import endava.internship.traininglicensessharing.domain.entity.Request;
import endava.internship.traininglicensessharing.domain.entity.User;
import endava.internship.traininglicensessharing.domain.entity.Workplace;
import endava.internship.traininglicensessharing.integration.cucumber.glue.context.ContextEnum;
import endava.internship.traininglicensessharing.integration.cucumber.glue.context.TestContext;
import endava.internship.traininglicensessharing.application.dto.LicenseDtoResponse;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CommonSteps extends BaseUtilFunctionality {

    public CommonSteps(TestContext testContext) {
        super(testContext);
    }

    @Before
    public void beforeScenario() {
        List<User> userList = (List<User>) scenarioContext.getContext(ContextEnum.USERS);
        List<License> licenseList = (List<License>) scenarioContext.getContext(ContextEnum.LICENSES);
        List<Discipline> disciplineList = (List<Discipline>) scenarioContext.getContext(ContextEnum.DISCIPLINES);
        List<Position> positionList = (List<Position>) scenarioContext.getContext(ContextEnum.POSITIONS);
        List<DeliveryUnit> deliveryUnitList = (List<DeliveryUnit>) scenarioContext.getContext(ContextEnum.DELIVERY_UNITS);

        contextPersistence.deleteContextDisciplinesFromDb(disciplineList);
        contextPersistence.deleteContextPositionsFromDb(positionList);
        contextPersistence.deleteContextDeliveryUnitsFromDb(deliveryUnitList);
        contextPersistence.deleteContextUsersFromDb(userList);
        contextPersistence.deleteContextLicensesFromDb(licenseList);

        scenarioContext.clearContext();
        clearCache();
    }

    @After
    public void afterScenario() {
        List<User> userList = (List<User>) scenarioContext.getContext(ContextEnum.USERS);
        List<License> licenseList = (List<License>) scenarioContext.getContext(ContextEnum.LICENSES);
        List<Discipline> disciplineList = (List<Discipline>) scenarioContext.getContext(ContextEnum.DISCIPLINES);
        List<Position> positionList = (List<Position>) scenarioContext.getContext(ContextEnum.POSITIONS);
        List<DeliveryUnit> deliveryUnitList = (List<DeliveryUnit>) scenarioContext.getContext(ContextEnum.DELIVERY_UNITS);

        contextPersistence.deleteContextDisciplinesFromDb(disciplineList);
        contextPersistence.deleteContextPositionsFromDb(positionList);
        contextPersistence.deleteContextDeliveryUnitsFromDb(deliveryUnitList);
        contextPersistence.deleteContextUsersFromDb(userList);
        contextPersistence.deleteContextLicensesFromDb(licenseList);

        scenarioContext.clearContext();
        clearCache();
    }

    @Then("^return status (.+)$")
    public void thenReturnStatusCode(String expectedStatusCode) {
        validateStatusCode(expectedStatusCode);
    }

    @Given("^the following licenses exist$")
    public void givenTheFollowingLicenseExists(final List<License> licenses) {
        licenses.forEach(this::theFollowingLicensesExists);
    }

    @Given("^the following requests exist")
    public void givenTheFollowingRequestsExist(final List<Request> requests) {
        requests.forEach(this::theFollowingRequestsExists);
    }

    @And("the licenses persist")
    public void theFollowingLicensesPersist() {
        List<License> licenseList = (List<License>) scenarioContext.getContext(ContextEnum.LICENSES);
        contextPersistence.persistContextLicenses(licenseList);
    }

    @When("GET request to {string} is sent")
    public void getRequestDashboardUserIsSent(String endpoint) {
        responseEntity = testRestTemplate.getForEntity(endpoint, String.class);
    }

    @When("^get license by id request is sent for (.+)$")
    public void whenGetLicenseByIdRequestIsSent(String name) {
        Optional<License> optionalLicense = contextPersistence.retrieveLicenseByName(name);
        optionalLicense.ifPresent(license -> id = license.getId());
        responseEntity = testRestTemplate.getForEntity("/licenses/" + id, String.class);
    }

    @And("^the license is returned$")
    public void andLicenseIsReturnedFromDatabase() throws JsonProcessingException {
        licenseDtoResponse = objectMapper.readValue(responseEntity.getBody(), LicenseDtoResponse.class);
    }
    @And("^the following positions exist$")
    public void givenTheFollowingPositionsExists(final List<Position> positions) {
        positions.forEach(this::theFollowingPositionsExist);
    }

    @And("the positions persist")
    public void givenTheFollowingPositionsPersist() {
        List<Position> positionList = (List<Position>) scenarioContext.getContext(ContextEnum.POSITIONS);
        contextPersistence.persistContextPositions(positionList);
    }

    @And("the following delivery units exist")
    public void givenTheDeliveryUnitExist(final List<DeliveryUnit> deliveryUnitList) {
        deliveryUnitList.forEach(this::theFollowingDeliveryUnitsExist);
    }

    @And("the delivery units persist")
    public void givenTheDeliveryUnitsPersist() {
        List<DeliveryUnit> deliveryUnitList = (List<DeliveryUnit>) scenarioContext.getContext(ContextEnum.DELIVERY_UNITS);
        contextPersistence.persistDeliveryUnits(deliveryUnitList);
    }

    @And("the workplaces persist")
    public void givenTheWorkplacesPersist() {
        List<Workplace> workplaceList = (List<Workplace>) scenarioContext.getContext(ContextEnum.WORKPLACES);
        contextPersistence.persistContextWorkplace(workplaceList);
    }

}
