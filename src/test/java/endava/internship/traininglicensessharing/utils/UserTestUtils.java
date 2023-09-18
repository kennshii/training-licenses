package endava.internship.traininglicensessharing.utils;

import static java.time.LocalDate.now;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import endava.internship.traininglicensessharing.application.dto.UserAdminDto;
import endava.internship.traininglicensessharing.application.dto.UserDisableDto;
import endava.internship.traininglicensessharing.application.dto.UserListAndRoleDto;
import endava.internship.traininglicensessharing.domain.entity.DeliveryUnit;
import endava.internship.traininglicensessharing.domain.entity.Discipline;
import endava.internship.traininglicensessharing.domain.entity.Position;
import endava.internship.traininglicensessharing.domain.entity.Role;
import endava.internship.traininglicensessharing.domain.entity.User;
import endava.internship.traininglicensessharing.domain.entity.Workplace;
import endava.internship.traininglicensessharing.domain.enums.Status;
import endava.internship.traininglicensessharing.domain.enums.UserRole;

public class UserTestUtils {
    private static final DeliveryUnit DELIVERY_UNIT = DeliveryUnit.builder()
            .name("MDD")
            .build();

    private static final List<Position> POSITION_LIST = List.of(
            Position.builder()
                    .name("Senior Tester")
                    .build(),
            Position.builder()
                    .name("Middle Java Developer")
                    .build()
    );
    private static final List<Discipline> DISCIPLINE_LIST = List.of(
            Discipline.builder()
                    .name("Testing")
                    .build(),
            Discipline.builder()
                    .name("Development")
                    .build()
    );


    public static final List<Workplace> WORKPLACE_LIST = List.of(
            Workplace.builder()
                    .deliveryUnit(DELIVERY_UNIT)
                    .position(POSITION_LIST.get(0))
                    .discipline(DISCIPLINE_LIST.get(0))
                    .build(),
            Workplace.builder()
                    .deliveryUnit(DELIVERY_UNIT)
                    .position(POSITION_LIST.get(1))
                    .discipline(DISCIPLINE_LIST.get(1))
                    .build()
    );
    private static final List<Role> ROLE_LIST = List.of(
            Role.builder()
                    .name(UserRole.ADMIN)
                    .build(),
            Role.builder()
                    .name(UserRole.REVIEWER)
                    .build(),
            Role.builder()
                    .name(UserRole.USER)
                    .build()
    );


    public static final List<User> USER_LIST = List.of(
            User.builder()
                    .firstName("FirstName1")
                    .lastName("LastName1")
                    .email("FirstName1.LastName1@endava.com")
                    .roles(new HashSet<>(Set.of(ROLE_LIST.get(0), ROLE_LIST.get(1))))
                    .isActive(true)
                    .lastActive(LocalDate.of(now().getYear(), now().getMonth().getValue() - 1, 10))
                    .registrationDate(LocalDate.of(now().getYear(), now().getMonth().getValue() - 2, 1))
                    .workplace(WORKPLACE_LIST.get(0))
                    .build(),
            User.builder()
                    .firstName("FirstName2")
                    .lastName("LastName2")
                    .email("FirstName2.LastName2@endava.com")
                    .roles(new HashSet<>(Set.of(ROLE_LIST.get(0), ROLE_LIST.get(1), ROLE_LIST.get(2))))
                    .isActive(true)
                    .lastActive(LocalDate.of(now().getYear(), now().getMonth().getValue() - 2, 11))
                    .registrationDate(LocalDate.of(now().getYear(), now().getMonth().getValue() - 3, 11))
                    .workplace(WORKPLACE_LIST.get(0))
                    .build()
    );

    public static final User USER = User.builder()
            .firstName("FirstName1")
            .lastName("LastName1")
            .email("FirstName1.LastName1@endava.com")
            .roles(Set.of(ROLE_LIST.get(0), ROLE_LIST.get(1)))
            .isActive(true)
            .lastActive(LocalDate.now().minusDays(10))
            .registrationDate(LocalDate.now().minusDays(15))
            .workplace(WORKPLACE_LIST.get(0))
            .build();

    public static final UserAdminDto USER_ADMIN_DTO = UserAdminDto.builder()
            .id(USER.getUserId())
            .role(UserRole.ADMIN.toString())
            .email(USER.getEmail())
            .companyRole(POSITION_LIST.get(0).getName())
            .discipline(DISCIPLINE_LIST.get(0).getName())
            .deliveryUnit(DELIVERY_UNIT.getName())
            .lastActiveTime(10)
            .lastActivePeriod("days")
            .status(Status.ACTIVE)
            .name(USER.getFirstName() + " " + USER.getLastName())
            .build();

    public static final UserListAndRoleDto USER_LIST_AND_ROLE_DTO =
            UserListAndRoleDto.builder()
                    .userIdList(new Integer[]{1, 2})
                    .roleId(1)
                    .build();
    public static final UserDisableDto USER_DISABLE_DTO = UserDisableDto.builder()
            .userIdList(new Integer[]{1, 2, 3})
            .build();
}
