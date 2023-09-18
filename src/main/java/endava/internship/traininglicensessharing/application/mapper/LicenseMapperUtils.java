package endava.internship.traininglicensessharing.application.mapper;

import endava.internship.traininglicensessharing.domain.entity.License;

public interface LicenseMapperUtils {

    String YEAR = "YEAR";
    String MONTH = "MONTH";
    String MONTHS = "MONTHS";

    default String getDurationUnit(License license, int licenseDuration) {
        if (licenseDuration >= 12) {
            return YEAR;
        } else if (licenseDuration == 1) {
            return MONTH;
        } else {
            return MONTHS;
        }
    }
}
