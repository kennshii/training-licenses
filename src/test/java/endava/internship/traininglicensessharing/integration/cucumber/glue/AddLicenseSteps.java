package endava.internship.traininglicensessharing.integration.cucumber.glue;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import endava.internship.traininglicensessharing.integration.cucumber.glue.context.TestContext;
import endava.internship.traininglicensessharing.application.dto.CredentialDto;
import endava.internship.traininglicensessharing.application.dto.LicenseDtoRequest;
import endava.internship.traininglicensessharing.application.dto.LicenseDtoResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddLicenseSteps extends BaseUtilFunctionality {

    private LicenseDtoRequest licenseDtoRequest;

    private CredentialDto credentialDto;

    public AddLicenseSteps(TestContext testContext) {
        super(testContext);
    }

    @When("^create license request$")
    public void whenCreateLicenseRequest(final LicenseDtoRequest licenseDtoRequest) {
        this.licenseDtoRequest = LicenseDtoRequest.builder()
                .name(licenseDtoRequest.getName())
                .website(licenseDtoRequest.getWebsite())
                .description(licenseDtoRequest.getDescription())
                .cost(licenseDtoRequest.getCost())
                .licenseDuration(licenseDtoRequest.getLicenseDuration())
                .licenseType(licenseDtoRequest.getLicenseType())
                .logo(licenseDtoRequest.getLogo())
                .isRecurring(licenseDtoRequest.getIsRecurring())
                .seatsTotal(licenseDtoRequest.getSeatsTotal())
                .expiresOn(licenseDtoRequest.getExpiresOn())
                .currency(licenseDtoRequest.getCurrency())
                .build();

        theFollowingLicensesExists(licenseMapper.licenseDtoRequestToLicense(licenseDtoRequest));
    }

    @And("^add new credentials for request")
    public void andAddNewCredentialsForRequest(final CredentialDto credentialDto) {
        this.credentialDto = credentialDto;
        this.licenseDtoRequest.setCredentials(List.of(this.credentialDto));
    }

    @And("^send an added license request$")
    public void andSendAnAddedLicenseRequest() {
        responseEntity = testRestTemplate.postForEntity("/licenses", licenseDtoRequest, String.class);
    }

    @Then("^return the following license response$")
    public void thenReturnLicenseResponse(final LicenseDtoResponse licenseDtoResponse) throws JsonProcessingException {
        LicenseDtoResponse response = objectMapper.readValue(responseEntity.getBody(), LicenseDtoResponse.class);
        licenseDtoResponse.setCredentials(List.of(this.credentialDto));

        assertThat(response).isEqualTo(licenseDtoResponse);
    }

}
