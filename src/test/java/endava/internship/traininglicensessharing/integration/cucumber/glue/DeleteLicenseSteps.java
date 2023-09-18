package endava.internship.traininglicensessharing.integration.cucumber.glue;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.Optional;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;

import endava.internship.traininglicensessharing.domain.entity.License;
import endava.internship.traininglicensessharing.integration.cucumber.glue.context.TestContext;
import io.cucumber.java.en.When;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class DeleteLicenseSteps extends BaseUtilFunctionality {

    @LocalServerPort
    String port;

    public DeleteLicenseSteps(TestContext testContext) {
        super(testContext);
    }

    @When("request delete license on endpoint {string}")
    public void requestDeleteLicense(String url) {
        responseEntity = new TestRestTemplate().exchange("http://localhost:" + port + url, HttpMethod.DELETE, null, String.class);
    }

    @When("request delete license")
    public void requestDeleteLicenses() {
        Optional<License> license = contextPersistence.retrieveLicenseByName("FANCYLICENSE");
        String url = "/licenses/" + license.get().getId();

        responseEntity = new TestRestTemplate().exchange("http://localhost:" + port + url, HttpMethod.DELETE, null, String.class);
    }

}
