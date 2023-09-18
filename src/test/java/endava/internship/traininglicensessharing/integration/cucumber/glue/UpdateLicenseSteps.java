package endava.internship.traininglicensessharing.integration.cucumber.glue;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.core.JsonProcessingException;

import endava.internship.traininglicensessharing.integration.cucumber.glue.context.TestContext;
import endava.internship.traininglicensessharing.integration.cucumber.glue.mapper.CucumberLicenseMapper;
import endava.internship.traininglicensessharing.application.dto.CredentialDto;
import endava.internship.traininglicensessharing.application.dto.LicenseDtoRequest;
import endava.internship.traininglicensessharing.application.dto.LicenseDtoResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class UpdateLicenseSteps extends BaseUtilFunctionality {

    private LicenseDtoRequest updatedLicense;

    @Autowired
    private CucumberLicenseMapper licenseMapper;

    private final List<CredentialDto> credentialDtos = new ArrayList<>();

    public UpdateLicenseSteps(TestContext testContext) {
        super(testContext);
    }

    @And("^update the license$")
    public void andUpdateTheLicense(LicenseDtoRequest licenseDtoRequest) {
        updatedLicense = updateLicense(licenseDtoRequest);
        addToTestContext(updatedLicense);
    }

    @And("^update license request is sent")
    public void andUpdateLicenseRequestIsSent() {
        responseEntity = testRestTemplate.exchange(
                "/licenses/" + licenseDtoResponse.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(updatedLicense),
                String.class);
    }

    @Then("the updated license is returned")
    public void thenTheUpdatedLicenseIsReturned() throws JsonProcessingException {
        final LicenseDtoResponse response = objectMapper.readValue(responseEntity.getBody(), LicenseDtoResponse.class);
        assertThat(response).isEqualTo(licenseMapper.licenseDtoRequestToLicenseDtoResponse(updatedLicense));
    }

    private LicenseDtoRequest updateLicense(LicenseDtoRequest licenseDtoRequest) {
        if (licenseDtoRequest.getName() == null)
            licenseDtoRequest.setName(licenseDtoResponse.getName());

        if (licenseDtoRequest.getWebsite() == null)
            licenseDtoRequest.setWebsite(licenseDtoResponse.getWebsite());

        if (licenseDtoRequest.getDescription() == null)
            licenseDtoRequest.setDescription(licenseDtoResponse.getDescription());

        if (licenseDtoRequest.getLicenseType() == null)
            licenseDtoRequest.setLicenseType(licenseDtoResponse.getLicenseType());

        if (licenseDtoRequest.getLicenseDuration() == null)
            licenseDtoRequest.setLicenseDuration(licenseDtoResponse.getLicenseDuration());

        if (licenseDtoRequest.getLogo() == null)
            licenseDtoRequest.setLogo(licenseDtoResponse.getLogo());

        if (licenseDtoRequest.getCurrency() == null)
            licenseDtoRequest.setCurrency(licenseDtoResponse.getCurrency());

        if (licenseDtoRequest.getIsRecurring() == null)
            licenseDtoRequest.setIsRecurring(licenseDtoResponse.getIsRecurring());

        if (licenseDtoRequest.getExpiresOn() == null)
            licenseDtoRequest.setExpiresOn(licenseDtoResponse.getExpiresOn());

        if (licenseDtoRequest.getSeatsTotal() == null)
            licenseDtoRequest.setSeatsTotal(licenseDtoResponse.getSeatsTotal());

        if (licenseDtoRequest.getCost() == null)
            licenseDtoRequest.setCost(licenseDtoResponse.getCost());

        if (licenseDtoRequest.getCredentials() == null) {
            licenseDtoRequest.setCredentials(licenseDtoResponse.getCredentials());
        }

        return licenseDtoRequest;
    }
}
