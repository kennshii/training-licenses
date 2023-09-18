package endava.internship.traininglicensessharing.application.mapper;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import endava.internship.traininglicensessharing.application.dto.LicenseUnusedDto;
import endava.internship.traininglicensessharing.domain.entity.License;

@Mapper
public interface LicenseUnusedMapper extends LicenseMapperUtils {

    LicenseUnusedMapper INSTANCE = Mappers.getMapper(LicenseUnusedMapper.class);

    @Mapping(target = "durationUnit", expression = "java(checkDurationUnit(license))")
    @Mapping(target = "period", expression = "java(calculatePeriod(license))")
    LicenseUnusedDto toLicenseUnusedDto(License license);

    default String checkDurationUnit(License license) {
        int months = getUnusedPeriod(license);
        return getDurationUnit(license, months);
    }

    default Integer calculatePeriod(License license) {
        int months = getUnusedPeriod(license);
        return months / 12 != 0 ? months / 12 : months;
    }

    private static Integer getUnusedPeriod(License license) {
        int licenseDuration = license.getLicenseDuration();
        LocalDate startDate = license.getExpiresOn().minusMonths(licenseDuration);
        long months = ChronoUnit.MONTHS.between(startDate, LocalDate.now());
        return Math.toIntExact(months);
    }
}
