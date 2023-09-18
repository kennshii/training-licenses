package endava.internship.traininglicensessharing.integration.cucumber.glue;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;

import endava.internship.traininglicensessharing.application.mapper.LicenseExpiringMapper;
import endava.internship.traininglicensessharing.application.mapper.LicenseUnusedMapper;
import endava.internship.traininglicensessharing.domain.entity.Discipline;
import endava.internship.traininglicensessharing.domain.entity.License;
import endava.internship.traininglicensessharing.domain.entity.Request;
import endava.internship.traininglicensessharing.domain.entity.User;
import endava.internship.traininglicensessharing.integration.cucumber.glue.context.ContextEnum;
import endava.internship.traininglicensessharing.integration.cucumber.glue.context.TestContext;
import endava.internship.traininglicensessharing.integration.cucumber.glue.dto.CucumberLicenseHistoryUpdateDto;
import endava.internship.traininglicensessharing.integration.cucumber.glue.dto.CucumberRequestDto;
import endava.internship.traininglicensessharing.integration.cucumber.glue.dto.CucumberUserDto;
import endava.internship.traininglicensessharing.integration.cucumber.glue.dto.CucumberWorkplaceDto;
import endava.internship.traininglicensessharing.integration.cucumber.glue.mapper.CucumberRequestMapper;
import endava.internship.traininglicensessharing.integration.cucumber.glue.mapper.CucumberUserMapper;
import endava.internship.traininglicensessharing.integration.cucumber.glue.mapper.CucumberWorkplaceMapper;
import endava.internship.traininglicensessharing.application.dto.AverageCostsDto;
import endava.internship.traininglicensessharing.application.dto.LicenseCostDto;
import endava.internship.traininglicensessharing.application.dto.LicenseExpiringDto;
import endava.internship.traininglicensessharing.application.dto.LicenseUnusedDto;
import endava.internship.traininglicensessharing.application.dto.UsersOverviewDto;
import endava.internship.traininglicensessharing.utils.DashboardTestUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class DashboardSteps extends BaseUtilFunctionality {

    @Autowired
    private LicenseExpiringMapper licenseExpiringMapper;

    @Autowired
    private LicenseUnusedMapper licenseUnusedMapper;

    public DashboardSteps(TestContext testContext) {
        super(testContext);
    }

    @Given("^the following disciplines exist$")
    public void givenTheFollowingDisciplinesExist(final List<Discipline> disciplines) {
        disciplines.forEach(this::theFollowingDisciplinesExist);
    }

    @Given("^the following workplaces exist$")
    public void givenTheFollowingWorkplacesExist(final List<CucumberWorkplaceDto> cucumberWorkplaceDtoList) {
        cucumberWorkplaceDtoList.forEach(cucumberWorkplaceDto ->
                theFollowingWorkplacesExist(CucumberWorkplaceMapper.cucumberWorkplaceDtoToWorkplace(cucumberWorkplaceDto, scenarioContext))
        );
    }

    @Given("^the following users exist$")
    public void givenTheFollowingUsersExist(final List<CucumberUserDto> cucumberUserDtoList) {
        cucumberUserDtoList.forEach(cucumberUserDto ->
                theFollowingUsersExist(CucumberUserMapper.cucumberUserDtoToUser(cucumberUserDto, scenarioContext))
        );
    }

    @Given("^the following requests exists$")
    public void givenTheFollowingRequestsExist(final List<CucumberRequestDto> cucumberRequestDtoList) {
        cucumberRequestDtoList.forEach(cucumberRequestDto ->
                theFollowingRequestsExists(CucumberRequestMapper.cucumberRequestDtoToRequest(cucumberRequestDto, scenarioContext))
        );
    }

    @And("the requests persist")
    public void theRequestPersist() {
        List<Request> requestList = (List<Request>) scenarioContext.getContext(ContextEnum.REQUESTS);
        contextPersistence.persistContextRequests(requestList);
    }

    @And("the disciplines persist")
    public void theFollowingDisciplinesPersist() {
        List<Discipline> disciplineList = (List<Discipline>) scenarioContext.getContext(ContextEnum.DISCIPLINES);
        contextPersistence.persistContextDisciplines(disciplineList);
    }

    @And("the users persist")
    public void theUsersPersist() {
        List<User> userList = (List<User>) scenarioContext.getContext(ContextEnum.USERS);
        contextPersistence.persistContextUsers(userList);
    }

    @Then("^the user overview dto is returned$")
    public void thenTheUserOverviewDtoIsReturned() throws JsonProcessingException {
        List<User> expectedUsers = (List<User>) scenarioContext.getContext(ContextEnum.USERS);
        List<Discipline> expectedDisciplines = (List<Discipline>) scenarioContext.getContext(ContextEnum.DISCIPLINES);

        UsersOverviewDto expectedUsersOverviewDto = DashboardTestUtils.setUpExpectedUsersOverviewDto(expectedUsers, expectedDisciplines);
        UsersOverviewDto actualUsersOverviewDto = objectMapper.readValue(responseEntity.getBody(), UsersOverviewDto.class);

        assertAll(
                () -> assertEquals(expectedUsersOverviewDto.getTotalUsers(), actualUsersOverviewDto.getTotalUsers()),
                () -> assertEquals(expectedUsersOverviewDto.getDeltaUsers(), actualUsersOverviewDto.getDeltaUsers()),
                () -> assertEquals(expectedUsersOverviewDto.getTotalDisciplines(), actualUsersOverviewDto.getTotalDisciplines()),
                () -> assertEquals(expectedUsersOverviewDto.getNumberOfUsersPerDiscipline(), actualUsersOverviewDto.getNumberOfUsersPerDiscipline())
        );
    }

    @Then("the average costs dto is returned")
    public void theAverageCostsDtoIsReturned() throws JsonProcessingException {
        List<User> expectedUsers = (List<User>) scenarioContext.getContext(ContextEnum.USERS);
        List<Discipline> expectedDisciplines = (List<Discipline>) scenarioContext.getContext(ContextEnum.DISCIPLINES);
        List<License> expectedLicenses = (List<License>) scenarioContext.getContext(ContextEnum.LICENSES);
        List<Request> expectedRequests = (List<Request>) scenarioContext.getContext(ContextEnum.REQUESTS);

        AverageCostsDto expectedAverageCostsDto = DashboardTestUtils.setUpExpectedAverageCostsDto(expectedLicenses, expectedUsers, expectedDisciplines);
        AverageCostsDto actualAverageCostsDto = objectMapper.readValue(responseEntity.getBody(), AverageCostsDto.class);

        assertAll(
                () -> assertEquals(expectedAverageCostsDto.getAverageCostPerUser(), actualAverageCostsDto.getAverageCostPerUser()),
                () -> assertEquals(expectedAverageCostsDto.getAverageCostPerDepartmentsMap(), actualAverageCostsDto.getAverageCostPerDepartmentsMap())
        );
    }

    @Then("^the list of expiring dto licenses is returned$")
    public void thenTheListOfExpiringDtpLicensesIsReturned() throws JsonProcessingException {
        List<License> licenseList = (List<License>) scenarioContext.getContext(ContextEnum.LICENSES);

        List<LicenseExpiringDto> expectedExpiringLicenseList = DashboardTestUtils.getExpectedListOfLicenseExpiringDto(licenseList, licenseExpiringMapper);
        expectedExpiringLicenseList.stream()
                .peek(licenseExpiringDto -> {
                    Optional<License> license = contextPersistence.retrieveLicenseByName(licenseExpiringDto.getName());
                    licenseExpiringDto.setId(license.get().getId());
                }).toList();

        List<LicenseExpiringDto> actualExpiringLicenseList = Arrays.asList(objectMapper.readValue(responseEntity.getBody(), LicenseExpiringDto[].class));

        assertAll(
                () -> assertNotNull(responseEntity),
                () -> assertEquals(expectedExpiringLicenseList, actualExpiringLicenseList)
        );
    }

    @Then("^the list of unused dto licenses is returned$")
    public void thenTheListOfExpiringDtoLicensesIsReturned() throws JsonProcessingException {
        List<License> licenseList = (List<License>) scenarioContext.getContext(ContextEnum.LICENSES);
        List<Request> requestList = (List<Request>) scenarioContext.getContext(ContextEnum.REQUESTS);

        List<LicenseUnusedDto> expectedListOfUnusedLicenses = DashboardTestUtils.getExpectedListOfLicenseUnusedDto(licenseList, requestList, licenseUnusedMapper);
        expectedListOfUnusedLicenses.stream()
                .peek(licenseUnusedDto -> {
                    Optional<License> license = contextPersistence.retrieveLicenseByName(licenseUnusedDto.getName());
                    licenseUnusedDto.setId(license.get().getId());
                }).toList();

        List<LicenseUnusedDto> actualExpiringLicenseList = Arrays.asList(objectMapper.readValue(responseEntity.getBody(), LicenseUnusedDto[].class));

        assertAll(
                () -> assertNotNull(responseEntity),
                () -> assertEquals(expectedListOfUnusedLicenses, actualExpiringLicenseList)
        );
    }

    @Given("^the following license_history data are updated$")
    public void givenTheFollowingLicenseHistoryDataAreUpdated(List<CucumberLicenseHistoryUpdateDto> licenseHistoryUpdateDtoList) {
        licenseHistoryUpdateDtoList.forEach(licenseHistoryUpdateDto ->
                contextPersistence.updateLicenseHistoryTable(licenseHistoryUpdateDto.getStartDate(), licenseHistoryUpdateDto.getLicense())
        );
    }

    @Then("^the costs overview dto is returned$")
    public void thenTheCostsOverviewDtoIsReturned() throws JsonProcessingException {
        LicenseCostDto actualLicenseCostDto = objectMapper.readValue(responseEntity.getBody(), LicenseCostDto.class);
        LicenseCostDto expectedLicenseCostsDTO = DashboardTestUtils.LICENSE_COST_DTO;

        assertAll(
                () -> assertEquals(expectedLicenseCostsDTO.getCostsReport(), actualLicenseCostDto.getCostsReport()),
                () -> assertEquals(expectedLicenseCostsDTO.getDeltaCosts(), actualLicenseCostDto.getDeltaCosts()),
                () -> assertEquals(expectedLicenseCostsDTO.getNumberOfSoftware(), actualLicenseCostDto.getNumberOfSoftware()),
                () -> assertEquals(expectedLicenseCostsDTO.getDeltaSoftware(), actualLicenseCostDto.getDeltaSoftware()),
                () -> assertEquals(expectedLicenseCostsDTO.getNumberOfTrainings(), actualLicenseCostDto.getNumberOfTrainings()),
                () -> assertEquals(expectedLicenseCostsDTO.getDeltaTrainings(), actualLicenseCostDto.getDeltaTrainings()),
                () -> assertEquals(expectedLicenseCostsDTO.getCostsForCurrentYear(), actualLicenseCostDto.getCostsForCurrentYear())
        );
    }

}
