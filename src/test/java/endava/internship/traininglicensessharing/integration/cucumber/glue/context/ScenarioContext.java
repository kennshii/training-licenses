package endava.internship.traininglicensessharing.integration.cucumber.glue.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import endava.internship.traininglicensessharing.domain.entity.Credential;
import endava.internship.traininglicensessharing.domain.entity.DeliveryUnit;
import endava.internship.traininglicensessharing.domain.entity.Discipline;
import endava.internship.traininglicensessharing.domain.entity.License;
import endava.internship.traininglicensessharing.domain.entity.Position;
import endava.internship.traininglicensessharing.domain.entity.Request;
import endava.internship.traininglicensessharing.domain.entity.Role;
import endava.internship.traininglicensessharing.domain.entity.User;
import endava.internship.traininglicensessharing.domain.entity.Workplace;
import endava.internship.traininglicensessharing.domain.enums.RequestStatus;
import endava.internship.traininglicensessharing.domain.enums.UserRole;
import lombok.ToString;

@Component
@ToString
public class ScenarioContext {

    private final Map<String, List<?>> scenarioContext;

    public ScenarioContext(){
        scenarioContext = new HashMap<>();
        clearContext();
    }

    public void clearContext() {
        scenarioContext.clear();
        scenarioContext.put(ContextEnum.LICENSES.toString(), new ArrayList<License>());
        scenarioContext.put(ContextEnum.DISCIPLINES.toString(), new ArrayList<Discipline>());
        scenarioContext.put(ContextEnum.WORKPLACES.toString(), new ArrayList<Workplace>());
        scenarioContext.put(ContextEnum.USERS.toString(), new ArrayList<User>());
        scenarioContext.put(ContextEnum.REQUESTS.toString(), new ArrayList<Request>());
        scenarioContext.put(ContextEnum.CREDENTIALS.toString(), new ArrayList<Credential>());
        scenarioContext.put(ContextEnum.POSITIONS.toString(), new ArrayList<Position>());
        scenarioContext.put(ContextEnum.DELIVERY_UNITS.toString(), new ArrayList<DeliveryUnit>());
        scenarioContext.put(ContextEnum.ROLES.toString(), new ArrayList<Role>());
        scenarioContext.put(ContextEnum.REQUEST_STATUS.toString(), new ArrayList<RequestStatus>());
    }

    public void setContext(ContextEnum key, List<?> value) {
        scenarioContext.put(key.toString(), value);
    }

    public Object getContext(ContextEnum key){
        return scenarioContext.get(key.toString());
    }

    public Discipline getTheDisciplineByName(String name) {
        List<Discipline> disciplineList = (List<Discipline>) getContext(ContextEnum.DISCIPLINES);

        return disciplineList.stream()
                .filter(x -> x.getName().equals(name))
                .findFirst()
                .get();
    }

    public License getTheLicenseByName(String name) {
        List<License> licenseList = (List<License>) getContext(ContextEnum.LICENSES);

        return licenseList.stream()
                .filter(x -> x.getName().equals(name))
                .findFirst()
                .get();
    }

    public User getTheUserByFirstNameAndLastName(String firstName, String lastName) {
        List<User> userList = (List<User>) getContext(ContextEnum.USERS);

        return userList.stream()
                .filter(x -> x.getLastName().equals(lastName))
                .filter(x -> x.getFirstName().equals(firstName))
                .findFirst()
                .get();
    }

    public Position getThePositionByName(String name) {
        List<Position> positionList = (List<Position>) getContext(ContextEnum.POSITIONS);

        return positionList.stream()
                .filter(x -> x.getName().equals(name))
                .findFirst()
                .get();
    }

    public DeliveryUnit getDeliveryUnitByName(String name) {
        List<DeliveryUnit> deliveryUnitList = (List<DeliveryUnit>) getContext(ContextEnum.DELIVERY_UNITS);

        return deliveryUnitList.stream()
                .filter(x -> x.getName().equals(name))
                .findFirst()
                .get();
    }

    public Set<Role> returnUserRolesSetByRole(String roleName) {
        UserRole role = UserRole.valueOf(roleName);

        Set<UserRole> userRoles = Arrays.stream(UserRole
                        .values())
                .toList()
                .stream()
                .filter(r -> r.ordinal() >= role.ordinal())
                .collect(Collectors.toSet());

        Set<Role> roles = new HashSet<>();
        userRoles
                .forEach(r -> roles.add(Role.builder()
                        .name(r)
                        .build()));

        return roles;
    }

}