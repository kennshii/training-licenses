package endava.internship.traininglicensessharing.integration.cucumber.glue;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

import endava.internship.traininglicensessharing.application.mapper.LicenseMapper;
import endava.internship.traininglicensessharing.application.mapper.UserMapper;
import endava.internship.traininglicensessharing.domain.entity.Credential;
import endava.internship.traininglicensessharing.domain.entity.DeliveryUnit;
import endava.internship.traininglicensessharing.domain.entity.Discipline;
import endava.internship.traininglicensessharing.domain.entity.License;
import endava.internship.traininglicensessharing.domain.entity.Position;
import endava.internship.traininglicensessharing.domain.entity.Request;
import endava.internship.traininglicensessharing.domain.entity.Role;
import endava.internship.traininglicensessharing.domain.entity.User;
import endava.internship.traininglicensessharing.domain.entity.Workplace;
import endava.internship.traininglicensessharing.integration.cucumber.glue.context.ContextEnum;
import endava.internship.traininglicensessharing.integration.cucumber.glue.context.ScenarioContext;
import endava.internship.traininglicensessharing.integration.cucumber.glue.context.TestContext;
import endava.internship.traininglicensessharing.integration.cucumber.glue.repository.ContextPersistence;
import endava.internship.traininglicensessharing.application.dto.LicenseDtoRequest;
import endava.internship.traininglicensessharing.application.dto.LicenseDtoResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseUtilFunctionality {

    @Autowired
    protected UserMapper userMapper;

    @Autowired
    protected ContextPersistence contextPersistence;

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Autowired
    protected ObjectMapper objectMapper;

    protected static ResponseEntity<String> responseEntity;

    protected ScenarioContext scenarioContext;

    protected static LicenseDtoResponse licenseDtoResponse;

    protected static Integer id = 0;

    @Autowired
    protected LicenseMapper licenseMapper;

    @Autowired
    protected CacheManager cacheManager;

    public BaseUtilFunctionality(TestContext testContext) {
        this.scenarioContext = testContext.getScenarioContext();
    }

    public void theFollowingLicensesExists(final License license) {
        List<License> licenseList = (List<License>) scenarioContext.getContext(ContextEnum.LICENSES);
        licenseList.add(license);
        scenarioContext.setContext(ContextEnum.LICENSES, licenseList);
    }

    public void theFollowingRequestsExists(final Request request) {
        List<Request> requestList = (List<Request>) scenarioContext.getContext(ContextEnum.REQUESTS);
        requestList.add(request);
        scenarioContext.setContext(ContextEnum.REQUESTS, requestList);
    }

    public void theFollowingDisciplinesExist(final Discipline discipline) {
        List<Discipline> disciplineList = (List<Discipline>) scenarioContext.getContext(ContextEnum.DISCIPLINES);
        disciplineList.add(discipline);
        scenarioContext.setContext(ContextEnum.DISCIPLINES, disciplineList);
    }

    public void theFollowingWorkplacesExist(final Workplace workplace) {
        List<Workplace> workplaceList = (List<Workplace>) scenarioContext.getContext(ContextEnum.WORKPLACES);
        workplaceList.add(workplace);
        scenarioContext.setContext(ContextEnum.WORKPLACES, workplaceList);
    }

    public void theFollowingUsersExist(final User user) {
        List<User> userList = (List<User>) scenarioContext.getContext(ContextEnum.USERS);
        userList.add(user);
        scenarioContext.setContext(ContextEnum.USERS, userList);
    }

    public void theFollowingCredentialExist(final Credential credential) {
        List<Credential> credentialList = (List<Credential>) scenarioContext.getContext(ContextEnum.CREDENTIALS);
        credentialList.add(credential);
        scenarioContext.setContext(ContextEnum.CREDENTIALS, credentialList);
    }

    public void theFollowingPositionsExist(final Position position) {
        List<Position> positionList = (List<Position>) scenarioContext.getContext(ContextEnum.POSITIONS);
        positionList.add(position);
        scenarioContext.setContext(ContextEnum.POSITIONS, positionList);
    }

    public void theFollowingDeliveryUnitsExist(final DeliveryUnit deliveryUnit) {
        List<DeliveryUnit> deliveryUnitList = (List<DeliveryUnit>) scenarioContext.getContext(ContextEnum.DELIVERY_UNITS);
        deliveryUnitList.add(deliveryUnit);
        scenarioContext.setContext(ContextEnum.DELIVERY_UNITS, deliveryUnitList);
    }

    public void theFollowingRolesExist(final Role role) {
        List<Role> roleList = (List<Role>) scenarioContext.getContext(ContextEnum.ROLES);
        roleList.add(role);
        scenarioContext.setContext(ContextEnum.ROLES, roleList);
    }

    public void validateStatusCode(String expectedStatusCode) {
        String actualStatusCode = responseEntity.getStatusCode().toString();

        assertThat(actualStatusCode).isEqualTo(expectedStatusCode);
    }

    public void addToTestContext(LicenseDtoRequest updatedLicense) {
        License license = licenseMapper.licenseDtoRequestToLicense(updatedLicense);
        theFollowingLicensesExists(license);
    }

    protected void clearCache() {
        for (String name:cacheManager.getCacheNames()){
            cacheManager.getCache(name).clear();
        }
    }
}
