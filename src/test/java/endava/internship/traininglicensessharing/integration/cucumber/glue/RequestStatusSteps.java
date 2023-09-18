package endava.internship.traininglicensessharing.integration.cucumber.glue;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.core.JsonProcessingException;

import endava.internship.traininglicensessharing.application.dto.RequestDtoRequest;
import endava.internship.traininglicensessharing.application.dto.RequestDtoResponse;
import endava.internship.traininglicensessharing.application.mapper.RequestMapper;
import endava.internship.traininglicensessharing.domain.entity.License;
import endava.internship.traininglicensessharing.domain.entity.Request;
import endava.internship.traininglicensessharing.domain.entity.User;
import endava.internship.traininglicensessharing.domain.enums.RequestStatus;
import endava.internship.traininglicensessharing.integration.cucumber.glue.context.ContextEnum;
import endava.internship.traininglicensessharing.integration.cucumber.glue.context.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RequestStatusSteps extends BaseUtilFunctionality {

    @Autowired
    private RequestMapper requestMapper;

    public RequestStatusSteps(final TestContext testContext) {
        super(testContext);
    }

    @And("request to update {} requests is sent with the new status {}")
    public void requestStatusChangeRequestIsSent(final String valid, final String status) {
        License contextLicense = ((List<License>) scenarioContext.getContext(ContextEnum.LICENSES)).get(0);
        User contextUser = ((List<User>) scenarioContext.getContext(ContextEnum.USERS)).get(0);

        List<Request> requests = contextPersistence.retrieveRequestById(contextUser.getLastName(), contextLicense.getName());

        List<Integer> requestIds = requests.stream()
                .filter(request -> !valid.equalsIgnoreCase("valid")
                        || !request.getStatus().equals(RequestStatus.REJECTED))
                .map(Request::getId)
                .toList();

        RequestDtoRequest editDtoRequest = RequestDtoRequest.builder()
                .requestIds(requestIds)
                .status(status).build();

        responseEntity = testRestTemplate.exchange(
                "/requests",
                HttpMethod.PUT,
                new HttpEntity<>(editDtoRequest),
                String.class
        );
    }

    @And("all the requests have this user")
    public void setRequestsUser() {
        List<Request> requests = (List<Request>) scenarioContext.getContext(ContextEnum.REQUESTS);
        List<User> users = (List<User>) scenarioContext.getContext(ContextEnum.USERS);
        User user = users.get(0);

        requests = requests.stream()
                .peek(request -> request.setUser(user))
                .toList();

        theFollowingUsersExist(user);
        requests.forEach(this::theFollowingRequestsExists);
    }

    @And("the requests have random user and license")
    public void setUserWorkplaceAndRole() {
        List<Request> requests = (List<Request>) scenarioContext.getContext(ContextEnum.REQUESTS);
        List<User> users = (List<User>) scenarioContext.getContext(ContextEnum.USERS);
        List<License> licenses = (List<License>) scenarioContext.getContext(ContextEnum.LICENSES);

        requests = requests.stream().peek(user -> {
            user.setUser(users.get(0));
            user.setLicense(licenses.get(0));
        }).toList();

        requests.forEach(this::theFollowingRequestsExists);
    }

    @Then("updated requests are returned with status {}")
    public void updatedRequestsAreReturned(final String status) throws JsonProcessingException {
        List<RequestDtoResponse> response =
                Arrays.asList(objectMapper.readValue(responseEntity.getBody(), RequestDtoResponse[].class));

        List<Request> contextRequests = (List<Request>) scenarioContext.getContext(ContextEnum.REQUESTS);

        List<RequestDtoResponse> expected = contextRequests.stream()
                .filter(r -> !r.getStatus().equals(RequestStatus.REJECTED))
                .map(requestMapper::requestToRequestDtoResponse)
                .peek(r -> r.setStatus(status))
                .toList();

        assertThat(response).isNotEmpty();
        assertThat(response.stream()
                .peek(r -> r.setRequestId(null))
                .toList()).containsAll(expected);
    }

    @When("get request for requests is sent")
    public void getRequestForRequestsIsSent() {
        responseEntity = testRestTemplate.exchange(
                "/requests",
                HttpMethod.GET,
                new HttpEntity<>(""),
                String.class
        );
    }

    @When("delete request for requests is sent")
    public void deleteRequestForRequestsIsSent() {
        License contextLicense = ((List<License>) scenarioContext.getContext(ContextEnum.LICENSES)).get(0);
        User contextUser = ((List<User>) scenarioContext.getContext(ContextEnum.USERS)).get(0);

        List<Request> requests = contextPersistence.retrieveRequestById(contextUser.getLastName(), contextLicense.getName());

        List<Integer> requestIds = requests.stream()
                .map(Request::getId)
                .toList();

        responseEntity = testRestTemplate.exchange(
                "/requests",
                HttpMethod.DELETE,
                new HttpEntity<>(requestIds),
                String.class
        );
    }

    @And("the added requests are not present")
    public void addedRequestsAreAbsent() throws JsonProcessingException {
        getRequestForRequestsIsSent();

        List<RequestDtoResponse> response =
                Arrays.asList(objectMapper.readValue(responseEntity.getBody(), RequestDtoResponse[].class));

        List<Request> contextRequests = (List<Request>) scenarioContext.getContext(ContextEnum.REQUESTS);

        List<RequestDtoResponse> expected = contextRequests.stream()
                .map(requestMapper::requestToRequestDtoResponse)
                .toList();

        response = response.stream()
                .peek(r -> r.setRequestId(null))
                .toList();

        assertThat(Collections.disjoint(response, expected)).isTrue();
    }

    @And("the added requests are present")
    public void addedRequestsAreReturned() throws JsonProcessingException {
        List<RequestDtoResponse> response =
                Arrays.asList(objectMapper.readValue(responseEntity.getBody(), RequestDtoResponse[].class));

        List<Request> contextRequests = (List<Request>) scenarioContext.getContext(ContextEnum.REQUESTS);

        List<RequestDtoResponse> expected = contextRequests.stream()
                .map(requestMapper::requestToRequestDtoResponse)
                .toList();

        assertThat(response).isNotEmpty();
        assertThat(response.stream()
                .peek(r -> r.setRequestId(null))
                .toList()).containsAll(expected);
    }

}
