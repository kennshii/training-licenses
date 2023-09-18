package endava.internship.traininglicensessharing.integration.cucumber.glue.repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
import endava.internship.traininglicensessharing.domain.repository.DeliveryUnitRepository;
import endava.internship.traininglicensessharing.domain.repository.DisciplineRepository;
import endava.internship.traininglicensessharing.domain.repository.LicenseRepository;
import endava.internship.traininglicensessharing.domain.repository.PositionRepository;
import endava.internship.traininglicensessharing.domain.repository.RequestRepository;
import endava.internship.traininglicensessharing.domain.repository.RoleRepository;
import endava.internship.traininglicensessharing.domain.repository.UserRepository;
import endava.internship.traininglicensessharing.domain.repository.WorkplaceRepository;
import endava.internship.traininglicensessharing.integration.cucumber.glue.context.ContextEnum;
import jakarta.transaction.Transactional;

@Component
public class ContextPersistence {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private DeliveryUnitRepository deliveryUnitRepository;

    @Autowired
    private WorkplaceRepository workplaceRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RequestRepository requestRepository;

    public void persistContextDisciplines(List<Discipline> disciplineList) {
        final String insertIntoDiscipline = "INSERT INTO discipline(name) VALUES(?)";

        disciplineList.forEach(discipline -> jdbcTemplate.update(insertIntoDiscipline, discipline.getName()));
    }

    public void persistContextPositions(List<Position> positionList) {
        final String insertIntoDiscipline = "INSERT INTO position(position_name) VALUES(?)";

        positionList.forEach(position -> jdbcTemplate.update(insertIntoDiscipline, position.getName()));
    }

    public void persistDeliveryUnits(List<DeliveryUnit> deliveryUnitList) {
        final String insertIntoDeliveryUnit = "INSERT INTO delivery_unit(delivery_unit_name) VALUES(?)";

        deliveryUnitList.forEach(deliveryUnit -> jdbcTemplate.update(insertIntoDeliveryUnit, deliveryUnit.getName()));
    }

    public void persistContextWorkplace(List<Workplace> workplaceList) {
        final String insertIntoWorkplace = "INSERT INTO workplace(position_id, delivery_unit_id, discipline_id) VALUES(?, ?, ?)";

        workplaceList.forEach(workplace -> {
            Optional<Discipline> disciplineByName = retrieveDisciplineByName(workplace.getDiscipline().getName());
            Optional<Position> positionByName = retrievePositionByName(workplace.getPosition().getName());
            Optional<DeliveryUnit> deliveryUnitByName = retrieveDeliveryUnitByName(workplace.getDeliveryUnit().getName());

            jdbcTemplate.update(insertIntoWorkplace,
                    positionByName.get().getId(),
                    deliveryUnitByName.get().getId(),
                    disciplineByName.get().getId()
            );
        });
    }

    public void persistContextLicenses(List<License> licenseList) {
        final String insertIntoLicense = "INSERT INTO license(name, website, description, logo, license_type, cost, currency, duration, expires_on, is_recurring, seats, is_test)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1)";

        licenseList.forEach((license) ->
                jdbcTemplate.update(insertIntoLicense,
                        license.getName(),
                        license.getWebsite(),
                        license.getDescription(),
                        license.getLogo(),
                        license.getLicenseType().toString(),
                        license.getCost(),
                        license.getCurrency().toString(),
                        license.getLicenseDuration(),
                        license.getExpiresOn(),
                        license.getIsRecurring(),
                        license.getSeats())
        );
    }

    public void persistContextRequests(List<Request> requestList) {
        final String insertIntoRequest = "INSERT INTO request(license_id, user_id, status, requested_on, start_of_use)" +
                "VALUES(?, ?, ?, ?, ?)";

        requestList.forEach((request) -> {
            Optional<User> userByName = retrieveUserByLastName(request.getUser().getLastName());
            Optional<License> licenseByName = retrieveLicenseByName(request.getLicense().getName());

            jdbcTemplate.update(insertIntoRequest,
                    licenseByName.get().getId(),
                    userByName.get().getUserId(),
                    request.getStatus().toString(),
                    request.getRequestedOn(),
                    request.getStartOfUse()
            );
        });
    }

    public void persistContextUsers(List<User> userList) {
        final String insertIntoUser = "INSERT INTO \"user\"(first_name, last_name, workplace_id, registration_date, is_active, last_active, is_test) " +
                "VALUES(?, ?, ?, ?, ?, ?, 1)";

        userList.forEach(user -> {
            Optional<Discipline> disciplineByName = retrieveDisciplineByName(user.getWorkplace().getDiscipline().getName());
            Optional<Position> positionByName = retrievePositionByName(user.getWorkplace().getPosition().getName());
            Optional<DeliveryUnit> deliveryUnitByName = retrieveDeliveryUnitByName(user.getWorkplace().getDeliveryUnit().getName());
            Optional<Workplace> workplaceByIds = retrieveWorkplaceByIds(
                    positionByName.get().getId(),
                    deliveryUnitByName.get().getId(),
                    disciplineByName.get().getId()
            );

            jdbcTemplate.update(insertIntoUser,
                    user.getFirstName(),
                    user.getLastName(),
                    workplaceByIds.get().getId(),
                    user.getRegistrationDate(),
                    user.getIsActive(),
                    user.getLastActive()
            );
        });
    }

    public void persistContextCredentials(List<Credential> credentialList) {
        final String insertIntoCredential = "INSERT INTO credential(username, password, license_id) " +
                "VALUES(?, ?, ?)";

        credentialList.forEach(credential -> {
            Optional<License> licenseByName = retrieveLicenseByName(credential.getLicense().getName());

            jdbcTemplate.update(insertIntoCredential,
                    credential.getUsername(),
                    credential.getPassword(),
                    licenseByName.get().getId()
            );
        });
    }

    public Optional<Discipline> retrieveDisciplineByName(String name) {
        return disciplineRepository.getDisciplineByName(name);
    }

    public List<Request> retrieveRequestById(String userName, String licenseName) {
        return requestRepository.getRequestsByUserLastNameAndLicenseName(userName, licenseName);
    }

    public Optional<Position> retrievePositionByName(String name) {
        return positionRepository.getPositionByName(name);
    }

    public Optional<DeliveryUnit> retrieveDeliveryUnitByName(String name) {
        return deliveryUnitRepository.getDeliveryUnitByName(name);
    }

    public Optional<Workplace> retrieveWorkplaceByIds(Integer positionId, Integer deliveryUnitId, Integer disciplineId) {
        return workplaceRepository.getWorkplaceByPositionIdAndDeliveryUnitIdAndDisciplineId(positionId, deliveryUnitId, disciplineId);
    }

    public Optional<User> retrieveUserByLastName(String lastName) {
        return userRepository.getUserByLastName(lastName);
    }

    public Optional<License> retrieveLicenseByName(String name) {
        return licenseRepository.getLicenseByName(name);
    }

    public void updateLicenseHistoryTable(LocalDate startDate, String licenseName) {
        final String updateLicenseHistory = "UPDATE license_history SET start_date = ? WHERE license_id = ?";

        Optional<License> licenseByName = retrieveLicenseByName(licenseName);

        jdbcTemplate.update(updateLicenseHistory,
                startDate,
                licenseByName.get().getId()
        );
    }

    public void deleteContextDisciplinesFromDb(List<Discipline> disciplineList) {
        disciplineList.forEach(discipline -> disciplineRepository.deleteDisciplineByName(discipline.getName()));
    }

    public void deleteContextPositionsFromDb(List<Position> positionList) {
        positionList.forEach(position -> positionRepository.deletePositionByName(position.getName()));
    }

    public void deleteContextDeliveryUnitsFromDb(List<DeliveryUnit> deliveryUnitList) {
        deliveryUnitList.forEach(deliveryUnit -> deliveryUnitRepository.deleteDeliveryUnitByName(deliveryUnit.getName()));
    }

    public void deleteContextUsersFromDb(List<User> userList) {
        userList.forEach(user -> userRepository.deleteUserByFirstNameAndLastName(user.getFirstName(), user.getLastName()));
    }

    public void deleteContextLicensesFromDb(List<License> licenseList) {
        licenseList.forEach(license -> licenseRepository.deleteLicenseByName(license.getName()));
    }

    @Transactional
    public HashMap<ContextEnum, List<?>> persistAllRelations(List<Role> roleList,
                                                             List<Position> positionList,
                                                             List<DeliveryUnit> deliveryUnitList,
                                                             List<Discipline> disciplineList,
                                                             List<Workplace> workplaceList,
                                                             List<User> userList) {
        List<Role> roles = roleRepository.saveAll(roleList);
        List<Position> positions = positionRepository.saveAll(positionList);
        List<DeliveryUnit> deliveryUnits = deliveryUnitRepository.saveAll(deliveryUnitList);
        List<Discipline> disciplines = disciplineRepository.saveAll(disciplineList);
        List<Workplace> workplaces = workplaceRepository.saveAll(workplaceList);
        List<User> users = userRepository.saveAll(userList);

        HashMap<ContextEnum, List<?>> modifiedHm = new HashMap<>();
        modifiedHm.put(ContextEnum.ROLES, roles);
        modifiedHm.put(ContextEnum.POSITIONS, positions);
        modifiedHm.put(ContextEnum.DELIVERY_UNITS, deliveryUnits);
        modifiedHm.put(ContextEnum.DISCIPLINES, disciplines);
        modifiedHm.put(ContextEnum.WORKPLACES, workplaces);
        modifiedHm.put(ContextEnum.USERS, users);

        return modifiedHm;
    }

    public List<Role> persistAllRoles(List<Role> roleList) {
        return roleRepository.saveAll(roleList);
    }
}
