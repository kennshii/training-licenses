package endava.internship.traininglicensessharing.integration.cucumber.glue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import endava.internship.traininglicensessharing.application.dto.RoleDto;
import endava.internship.traininglicensessharing.application.dto.UserAdminDto;
import endava.internship.traininglicensessharing.application.dto.UserDisableDto;
import endava.internship.traininglicensessharing.application.dto.UserListAndRoleDto;
import endava.internship.traininglicensessharing.application.mapper.UserMapper;
import endava.internship.traininglicensessharing.domain.entity.DeliveryUnit;
import endava.internship.traininglicensessharing.domain.entity.Discipline;
import endava.internship.traininglicensessharing.domain.entity.Position;
import endava.internship.traininglicensessharing.domain.entity.Role;
import endava.internship.traininglicensessharing.domain.entity.User;
import endava.internship.traininglicensessharing.domain.entity.Workplace;
import endava.internship.traininglicensessharing.domain.enums.Status;
import endava.internship.traininglicensessharing.domain.enums.UserRole;
import endava.internship.traininglicensessharing.domain.repository.DeliveryUnitRepository;
import endava.internship.traininglicensessharing.domain.repository.DisciplineRepository;
import endava.internship.traininglicensessharing.domain.repository.PositionRepository;
import endava.internship.traininglicensessharing.domain.repository.RoleRepository;
import endava.internship.traininglicensessharing.domain.repository.UserRepository;
import endava.internship.traininglicensessharing.domain.repository.WorkplaceRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersSteps {
    private List<User> userList;
    private List<UserAdminDto> expectedUserAdminDto = new ArrayList<>();
    private List<Position> positionList;
    private List<Discipline> disciplineList;
    private List<DeliveryUnit> deliveryUnitList;
    private List<Workplace> workplaceList = new ArrayList<>();
    private List<Role> roleList = new ArrayList<>();
    private String endpoint;
    private final UserDisableDto userDisableDto = new UserDisableDto();
    private int status;
    private Role roleToChange;
    private UserListAndRoleDto userListAndRoleDto;
    private Status userStatus;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private WorkplaceRepository workplaceRepository;
    @Autowired
    private DeliveryUnitRepository deliveryUnitRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private DisciplineRepository disciplineRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Given("^There are the next roles in the data base$")
    public void addRoles(List<Role> roleList) {
        this.roleList = roleRepository.findAll();

        assertThat(roleList).containsAll(this.roleList);
    }

    @Given("^There are the next positions$")
    public void addPositions(List<Position> positionList) {
        this.positionList = positionList;
    }

    @Given("^There are the next delivery units$")
    public void addDus(List<DeliveryUnit> deliveryUnitList) {
        this.deliveryUnitList = deliveryUnitList;
    }

    @Given("^There are the next disciplines$")
    public void addDisciplines(List<Discipline> disciplineList) {
        this.disciplineList = disciplineList;

    }

    @Given("^There are the next workplace$")
    public void createWorkplaces(List<Map<String, String>> workplaces) {
        workplaces.forEach(wp ->
                workplaceList
                        .add(Workplace
                                .builder()
                                .deliveryUnit(findDu(wp.get("deliveryUnit")))
                                .position(findPosition(wp.get("position")))
                                .discipline(findDiscipline(wp.get("discipline")))
                                .build()));
    }

    @Given("^There are the next users$")
    @Transactional
    public void createUsers(List<User> userList) {
        this.userList = userList;

        setWorkplaceForUsers();

        this.roleList = roleRepository.findAll();
        setRolesForUsers();

        saveDataInDataBase();
    }

    @When("^Admin uses (.+) endpoint$")
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @When("^It changes users' roles$")
    public void changeRole() {
        Integer[] usersId = new Integer[userList.size()];
        userList
                .stream()
                .map(User::getUserId)
                .toList()
                .toArray(usersId);

        userListAndRoleDto = UserListAndRoleDto
                .builder()
                .userIdList(usersId)
                .roleId(roleToChange.getId())
                .build();

        expectedUserAdminDto.forEach(user -> user.setRole(roleToChange.getName().toString()));
    }

    @When("^Admin decides to change returned users' role to (.*)$")
    public void changeUsersRole(String roleName) {
        roleList = roleRepository.findAll();

        roleToChange = roleList
                .stream()
                .filter(role -> role.getName() == UserRole.valueOf(roleName))
                .findFirst()
                .get();
    }

    @When("^It disables users$")
    public void disableUser() throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(APPLICATION_JSON);

        HttpEntity<String> httpEntity = new HttpEntity<>(objectMapper.writeValueAsString(userDisableDto), httpHeaders);

        assertThat(testRestTemplate.exchange(endpoint,
                        HttpMethod.PUT, httpEntity, Void.class)
                .getStatusCode().value())
                .isEqualTo(status);

        checkUserStatus();
    }


    @When("^Admin uses an invalid user id$")
    public void addAnInvalidUserId() {
        userListAndRoleDto.getUserIdList()[0] = -1;
    }

    @Then("^It should receive the list of all the users from the data base$")
    public void getAllUsers() {
        userList = new ArrayList<>(userRepository.findAll());

        convertUserToUsersAdminDTO();
    }

    @And("^These users should be the users defined above$")
    public void checkUsers() throws JsonProcessingException {
        String expected = objectMapper.writeValueAsString(expectedUserAdminDto);
        String returned = objectMapper.
                writeValueAsString(testRestTemplate.getForObject(endpoint, UserAdminDto[].class));

        assertThat(returned).isEqualTo(expected);
    }


    @Then("^It should receive the list of all the roles from the data base, except (.+) role$")
    public void fetchRoles(String userRole) {
        List<RoleDto> roleDtoList = List.of(testRestTemplate.getForObject(endpoint, RoleDto[].class));

        assertThat(roleDtoList.stream()
                .map(RoleDto::getName)
                .toList())
                .isNotEmpty()
                .doesNotContain(userRole);
    }


    @And("^It uses id of existing active users to disable them$")
    public void setUserId() {
        userDisableDto.setUserIdList(new Integer[userList.size()]);

        userRepository.findAll()
                .stream()
                .map(User::getUserId)
                .toList()
                .toArray(userDisableDto.getUserIdList());
    }


    @And("^Users' roles should be changed$")
    public void checkUsersRole() throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(APPLICATION_JSON);

        HttpEntity<String> httpEntity = new HttpEntity<>(objectMapper.writeValueAsString(userListAndRoleDto), httpHeaders);

        assertThat(testRestTemplate
                .exchange(endpoint, HttpMethod.PUT, httpEntity, UserListAndRoleDto.class)
                .getStatusCode().value()).isEqualTo(status);

        for (UserAdminDto userAdminDto : testRestTemplate.getForObject("/users", UserAdminDto[].class)) {
            assertThat(userAdminDto.getRole()).isEqualTo(roleToChange.getName().toString());
        }


    }


    @Then("It should receive {int} status")
    public void setStatus(int status) {
        this.status = status;
    }

    @Then("^Users should have (.*) status$")
    public void setUsersStatus(String status) {
        userStatus = Status.valueOf(status);
    }

    @But("^If it uses an id of nonexistent user$")
    public void setNonexistentUserId() {
        userDisableDto.getUserIdList()[0] = -1;
    }

    @And("^Inserted data should be deleted from the data base$")
    @Transactional
    public void insertedDataShouldBeDeletedFromTheDataBase() {
        userRepository.deleteAll(userList);
        workplaceRepository.deleteAll(workplaceList);
        deliveryUnitRepository.deleteAll(deliveryUnitList);
        positionRepository.deleteAll(positionList);
        disciplineRepository.deleteAll(disciplineList);
    }

    private Discipline findDiscipline(String disciplineName) {
        return disciplineList
                .stream()
                .filter(d -> d
                        .getName()
                        .equals(disciplineName))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    private Position findPosition(String positionName) {
        return positionList
                .stream()
                .filter(p -> p
                        .getName()
                        .equals(positionName))
                .toList()
                .get(0);
    }

    private DeliveryUnit findDu(String duName) {
        return deliveryUnitList
                .stream()
                .filter(deliveryUnit -> deliveryUnit
                        .getName()
                        .equals(duName))
                .toList()
                .get(0);
    }


    private void convertUserToUsersAdminDTO() {
        expectedUserAdminDto.clear();

        userList.forEach(user ->
                expectedUserAdminDto.add(userMapper.userToUserAdminDto(user)));
        expectedUserAdminDto = new ArrayList<>(expectedUserAdminDto.stream()
                .sorted(Comparator.comparingInt(dto -> UserRole.valueOf(dto.getRole().toUpperCase()).ordinal()))
                .toList());
    }

    private void setRolesForUsers() {
        userList.get(1).setRoles(Set.of(roleList.get(2)));
        userList.get(0).setRoles(Set.of(roleList.get(2), roleList.get(1)));
        userList.get(2).setRoles(Set.of(roleList.get(2), roleList.get(1), roleList.get(0)));
    }

    private void setWorkplaceForUsers() {
        userList.get(0).setWorkplace(workplaceList.get(0));
        userList.get(1).setWorkplace(workplaceList.get(1));
        userList.get(2).setWorkplace(workplaceList.get(1));
    }


    private void saveDataInDataBase() {
        this.positionList = positionRepository.saveAll(positionList);
        this.deliveryUnitList = deliveryUnitRepository.saveAll(deliveryUnitList);
        this.disciplineList = disciplineRepository.saveAll(disciplineList);
        this.workplaceList = workplaceRepository.saveAll(workplaceList);

        this.userList = userRepository.saveAll(this.userList);
    }

    private void checkUserStatus() {
        List<UserAdminDto> users = Arrays.stream(testRestTemplate.getForObject("/users", UserAdminDto[].class))
                .filter(user -> List.of(userDisableDto.getUserIdList()).contains(user.getId()))
                .toList();

        users.forEach(
                user -> assertThat(user.getStatus()).isEqualTo(userStatus)
        );
    }

}
