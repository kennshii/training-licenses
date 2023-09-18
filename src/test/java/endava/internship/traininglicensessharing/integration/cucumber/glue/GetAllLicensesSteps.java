package endava.internship.traininglicensessharing.integration.cucumber.glue;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;

import endava.internship.traininglicensessharing.application.mapper.LicenseMapper;
import endava.internship.traininglicensessharing.domain.entity.Credential;
import endava.internship.traininglicensessharing.domain.entity.License;
import endava.internship.traininglicensessharing.integration.cucumber.glue.context.ContextEnum;
import endava.internship.traininglicensessharing.integration.cucumber.glue.context.TestContext;
import endava.internship.traininglicensessharing.integration.cucumber.glue.mapper.CucumberCredentialMapper;
import endava.internship.traininglicensessharing.application.dto.CredentialDto;
import endava.internship.traininglicensessharing.application.dto.LicenseDtoResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class GetAllLicensesSteps extends BaseUtilFunctionality {

    @Autowired
    private LicenseMapper licenseMapper;

    public GetAllLicensesSteps(TestContext testContext) {
        super(testContext);
    }

    @Then("^the license detailed list is returned$")
    public void thenTheLicenseDetailedListIsReturned() throws JsonProcessingException {
        List<License> licenseList = (List<License>) scenarioContext.getContext(ContextEnum.LICENSES);

        List<LicenseDtoResponse> actualLicenseDtoResponses = Arrays.asList(objectMapper.readValue(responseEntity.getBody(), LicenseDtoResponse[].class));
        List<LicenseDtoResponse> expectedLicenseDtoResponses = licenseList.stream()
                .map(license -> licenseMapper.licenseToLicenseDtoResponse(license))
                .toList();

        assertThat(actualLicenseDtoResponses).containsAll(expectedLicenseDtoResponses);
    }

    @And("all the licenses have the following credentials")
    public void theLicensesHaveTheFollowingCredentials(final CredentialDto credentialDto) {
        List<License> licenseList = (List<License>) scenarioContext.getContext(ContextEnum.LICENSES);

        Credential credential = CucumberCredentialMapper.credentialDtoToCredential(credentialDto);
        credential.setLicense(licenseList.get(0));
        licenseList.forEach(license ->
                license.setCredentials(Collections.singletonList(credential))
        );
        theFollowingCredentialExist(credential);
    }

    @And("the credentials persist")
    public void theFollowingCredentialPersist() {
        List<Credential> credentialList = (List<Credential>) scenarioContext.getContext(ContextEnum.CREDENTIALS);
        contextPersistence.persistContextCredentials(credentialList);
    }

    @And("the new credentials persist")
    public void theNewFollowingCredentialPersist() {
        List<Credential> credentialList = (List<Credential>) scenarioContext.getContext(ContextEnum.CREDENTIALS);
        contextPersistence.persistContextCredentials(credentialList);
    }

}
