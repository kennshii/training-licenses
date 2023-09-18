package endava.internship.traininglicensessharing.application.mapper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

import endava.internship.traininglicensessharing.application.dto.UserAdminDto;
import endava.internship.traininglicensessharing.domain.entity.Role;
import endava.internship.traininglicensessharing.domain.entity.User;
import endava.internship.traininglicensessharing.domain.enums.Status;
import endava.internship.traininglicensessharing.domain.enums.TimePeriod;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "userId", target = "id")
    @Mapping(target = "name", expression = "java(getFullName(user))")
    @Mapping(target = "companyRole", expression = "java(user.getWorkplace().getPosition().getName())")
    @Mapping(target = "discipline", expression = "java(user.getWorkplace().getDiscipline().getName())")
    @Mapping(target = "deliveryUnit", expression = "java(user.getWorkplace().getDeliveryUnit().getName())")
    @Mapping(target = "status", expression = "java(getStatus(user))")
    @Mapping(target = "role", expression = "java(getRoles(user))")
    @Mapping(target = "lastActiveTime", expression = "java(getLastActiveTime(user))")
    @Mapping(target = "lastActivePeriod", expression = "java(getLastActivePeriod(user))")
    @Mapping(source = "email", target = "email")
    UserAdminDto userToUserAdminDto(User user);

    default String getFullName(User user) {
        return user.getFirstName() + " " + user.getLastName();
    }

    default Integer getInactivePeriodInDays(User user) {
        return Math.toIntExact(ChronoUnit.DAYS.between(user.getLastActive(), LocalDateTime.now()));
    }

    default Status getStatus(User user) {
        if (Boolean.FALSE.equals(user.getIsActive()))
            return Status.DEACTIVATED;
        else if (getInactivePeriodInMonths(user) < 12)
            return Status.ACTIVE;
        else
            return Status.INACTIVE;
    }

    default Integer getLastActiveTime(User user) {
        return getStatus(user).equals(Status.ACTIVE) ? getInactivePeriodInDays(user) : 1;
    }

    default String getLastActivePeriod(User user) {
        Integer inactiveDays = getInactivePeriodInDays(user);
        TimePeriod period = inactiveDays == 1 ? TimePeriod.DAY : TimePeriod.DAYS;
        return getStatus(user).equals(Status.ACTIVE) ? period.toString() : TimePeriod.YEAR.toString();
    }

    default String getRoles(User user) {
        if (CollectionUtils.isEmpty(user.getRoles())) {
            user.setRoles(new HashSet<>());
        }

        List<Role> roles = user.getRoles().stream()
                .sorted(Comparator.comparingInt(role -> role.getName().ordinal()))
                .toList();

        return user.getRoles().isEmpty() ? "" : roles.get(0).getName().toString();
    }

    default Integer getInactivePeriodInMonths(User user) {
        return Math.toIntExact(
                ChronoUnit.MONTHS.between(user.getLastActive(), LocalDateTime.now()));
    }

}
