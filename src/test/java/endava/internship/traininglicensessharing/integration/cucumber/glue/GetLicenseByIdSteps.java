package endava.internship.traininglicensessharing.integration.cucumber.glue;

import static org.assertj.core.api.Assertions.assertThat;

import endava.internship.traininglicensessharing.domain.entity.License;
import endava.internship.traininglicensessharing.integration.cucumber.glue.context.TestContext;
import endava.internship.traininglicensessharing.application.dto.LicenseDtoResponse;
import io.cucumber.java.en.Then;

public class GetLicenseByIdSteps extends BaseUtilFunctionality {

    public GetLicenseByIdSteps(TestContext testContext) {
        super(testContext);
    }

    @Then("^the license is equal to the existing license$")
    public void thenTheLicenseIsReturned() {
        final License expectedLicense = scenarioContext.getTheLicenseByName(licenseDtoResponse.getName());
        final LicenseDtoResponse expectedLicenseDtoResponse = licenseMapper.licenseToLicenseDtoResponse(expectedLicense);

        assertThat(licenseDtoResponse).isEqualTo(expectedLicenseDtoResponse);
    }
}
