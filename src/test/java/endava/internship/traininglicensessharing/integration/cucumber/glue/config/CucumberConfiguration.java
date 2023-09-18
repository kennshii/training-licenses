package endava.internship.traininglicensessharing.integration.cucumber.glue.config;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import endava.internship.traininglicensessharing.domain.entity.Role;
import endava.internship.traininglicensessharing.domain.entity.User;
import endava.internship.traininglicensessharing.domain.enums.UserRole;
import io.cucumber.java.DataTableType;
import io.cucumber.java.DefaultDataTableCellTransformer;
import io.cucumber.java.DefaultDataTableEntryTransformer;
import io.cucumber.java.DefaultParameterTransformer;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberConfiguration {

    private final ObjectMapper objectMapper;

    public CucumberConfiguration() {
        this.objectMapper = new ObjectMapper();
    }

    @DefaultDataTableCellTransformer
    @DefaultParameterTransformer
    @DefaultDataTableEntryTransformer
    public Object transform(final Object from, final Type to) {
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.convertValue(from, objectMapper.constructType(to));
    }

    @DataTableType
    public User convertToUser(Map<String, String> map) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return User.builder()
                .firstName(map.get("firstName"))
                .lastName(map.get("lastName"))
                .roles(getRolesOfUsers(map.get("role")))
                .registrationDate(LocalDate.parse(map.get("registrationDate"), formatter))
                .lastActive(LocalDate.parse(map.get("lastActive"), formatter))
                .isActive(Boolean.valueOf(map.get("isActive")))
                .build();
    }

    @DataTableType
    public Role convertToRole(Map<String, String> map) {
        return Role.builder()
                .name(UserRole.valueOf(map.get("name").toUpperCase()))
                .description(map.get("description"))
                .build();
    }

    private Set<Role> getRolesOfUsers(String roleName) {
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
