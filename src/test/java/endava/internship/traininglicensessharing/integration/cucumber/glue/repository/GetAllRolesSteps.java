package endava.internship.traininglicensessharing.integration.cucumber.glue.repository;

import static endava.internship.traininglicensessharing.utils.RoleTestUtils.ROLE_DTO_LIST;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import endava.internship.traininglicensessharing.application.dto.RoleDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAllRolesSteps {
    @Autowired
    private final TestRestTemplate testRestTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    private List<RoleDto> roleList = new ArrayList<>();
    private String endpoint;
    private List<RoleDto> excludeRoleList = new ArrayList<>();

    @Given("^The following roles exist$")
    public void createRolesList(List<RoleDto> roleList) {
        this.roleList.addAll(roleList);
    }

    @When("^The user uses the (.*) endpoint$")
    public void getEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @Then("^It should receive the list of all the roles from the data base, except$")
    public void getRoleToExclude(List<RoleDto> excludeRoleList) throws JsonProcessingException {
        this.excludeRoleList.addAll(excludeRoleList);
        roleList.removeAll(this.excludeRoleList);

        List<RoleDto> returned = List.of((testRestTemplate.getForObject(endpoint, RoleDto[].class)));

        assertThat(returned).isEqualTo(ROLE_DTO_LIST);

    }
}
