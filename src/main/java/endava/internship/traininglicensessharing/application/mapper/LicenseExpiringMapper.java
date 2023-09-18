package endava.internship.traininglicensessharing.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import endava.internship.traininglicensessharing.domain.entity.License;
import endava.internship.traininglicensessharing.application.dto.LicenseExpiringDto;

@Mapper
public interface LicenseExpiringMapper extends LicenseMapperUtils {

    LicenseExpiringMapper INSTANCE = Mappers.getMapper(LicenseExpiringMapper.class);

    @Mapping(target = "durationUnit", expression = "java(checkDurationUnit(license))")
    @Mapping(target = "licenseDuration", expression = "java(checkForYear(license))")
    LicenseExpiringDto toLicenseExpiringDto(License license);

    default String checkDurationUnit(License license) {
        int licenseDuration = license.getLicenseDuration();
        return getDurationUnit(license, licenseDuration);
    }

    default Integer checkForYear(License license) {
        if (license.getLicenseDuration() >= 12) {
            return 1;
        }
        return license.getLicenseDuration();
    }
}
