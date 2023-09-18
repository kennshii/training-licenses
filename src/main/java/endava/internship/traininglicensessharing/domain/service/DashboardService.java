package endava.internship.traininglicensessharing.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import endava.internship.traininglicensessharing.domain.entity.License;
import endava.internship.traininglicensessharing.domain.entity.LicenseCost;

public interface DashboardService {

    Long getTotalUsers();

    Long getDeltaUsers();

    Long getTotalDisciplines();

    Long getAverageCostPerUser();

    List<License> getExpiringLicenses();

    List<License> getUnusedLicenses();

    BigDecimal getCostsForPreviousYear();

    BigDecimal getCostsForCurrentYear();

    Long getNumberOfTrainingsForCurrentYear();

    Long getNumberOfTrainingsForPreviousYear();

    Long getDeltaForTrainings();

    Long getNumberOfSoftwareForCurrentYear();

    Long getNumberOfSoftwareForPreviousYear();

    Long getDeltaForSoftware();

    BigDecimal getDeltaForCosts();

    List<Map<String, Object>> getUsersByDisciplineCount();

    List<Map<String, Object>> getAverageCostPerUsersPerDiscipline();

    List<LicenseCost> getCostsForLast12Months();


}
