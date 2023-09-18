package endava.internship.traininglicensessharing.integration.cucumber;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"},
        glue = "endava.internship.traininglicensessharing.integration.cucumber.glue",
        features = "src/test/resources/features")
public class LicensesIntegrationTest {
}
