package endava.internship.traininglicensessharing.integration.cucumber.glue.context;

import org.springframework.stereotype.Component;

@Component
public class TestContext {

    private final ScenarioContext scenarioContext;

    public TestContext(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    public ScenarioContext getScenarioContext() {
        return scenarioContext;
    }
}
