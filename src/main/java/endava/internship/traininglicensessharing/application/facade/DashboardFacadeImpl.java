package endava.internship.traininglicensessharing.application.facade;

import static java.math.BigDecimal.valueOf;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import endava.internship.traininglicensessharing.application.dto.AverageCostsDto;
import endava.internship.traininglicensessharing.application.dto.DisciplineValueDto;
import endava.internship.traininglicensessharing.application.dto.LicenseCostDto;
import endava.internship.traininglicensessharing.application.dto.LicenseExpiringDto;
import endava.internship.traininglicensessharing.application.dto.LicenseUnusedDto;
import endava.internship.traininglicensessharing.application.dto.MonthDto;
import endava.internship.traininglicensessharing.application.dto.UsersOverviewDto;
import endava.internship.traininglicensessharing.application.mapper.LicenseExpiringMapper;
import endava.internship.traininglicensessharing.application.mapper.LicenseUnusedMapper;
import endava.internship.traininglicensessharing.domain.entity.LicenseCost;
import endava.internship.traininglicensessharing.domain.service.DashboardService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DashboardFacadeImpl implements DashboardFacade {

    private final DashboardService dashboardService;

    private final LicenseExpiringMapper licenseExpiringMapper;

    private final LicenseUnusedMapper licenseUnusedMapper;

    @Override
    public LicenseCostDto getCostsData() {
        return LicenseCostDto
                .builder()
                .numberOfTrainings(dashboardService.getNumberOfTrainingsForCurrentYear())
                .numberOfSoftware(dashboardService.getNumberOfSoftwareForCurrentYear())
                .deltaSoftware(dashboardService.getDeltaForSoftware())
                .deltaTrainings(dashboardService.getDeltaForTrainings())
                .costsReport(getCostsForLast12Months())
                .costsForCurrentYear(dashboardService.getCostsForCurrentYear())
                .deltaCosts(dashboardService.getDeltaForCosts())
                .build();
    }

    private List<MonthDto> getCostsForLast12Months() {
        List<LicenseCost> licenseCosts = dashboardService.getCostsForLast12Months();

        List<MonthDto> months = new ArrayList<>();

        licenseCosts.forEach(this::setDateToOne);
        Map<LocalDate, List<LicenseCost>> dateAndListsOfCosts = groupCostsByDate(licenseCosts);
        Map<LocalDate, BigDecimal> dateAndTotalCosts = new HashMap<>();

        dateAndListsOfCosts.forEach((key, value) -> countCostPerMonth(key, value, dateAndTotalCosts));
        dateAndTotalCosts.forEach((key, value) -> convertToMonthDTO(key, value, months));

        addMissingMonths(months);

        months.sort(null);

        return months;
    }

    @Override
    public UsersOverviewDto getUsersOverview() {
        return UsersOverviewDto.builder()
                .totalUsers(dashboardService.getTotalUsers())
                .deltaUsers(dashboardService.getDeltaUsers())
                .totalDisciplines(dashboardService.getTotalDisciplines())
                .numberOfUsersPerDiscipline(getUsersByDisciplineCount())
                .build();
    }

    private List<DisciplineValueDto> getUsersByDisciplineCount() {
        List<Map<String, Object>> userList = dashboardService.getUsersByDisciplineCount();

        return userList.stream()
                .map(user -> DisciplineValueDto.builder()
                        .id((Integer) user.get("id"))
                        .name((String) user.get("discipline"))
                        .value((Long) user.get("userNum"))
                        .build())
                .toList();
    }

    @Override
    public AverageCostsDto getAverageCostsDto() {
        return AverageCostsDto.builder()
                .averageCostPerUser(dashboardService.getAverageCostPerUser())
                .averageCostPerDepartmentsMap(getAverageCostPerUsersPerDiscipline())
                .build();
    }

    private List<DisciplineValueDto> getAverageCostPerUsersPerDiscipline() {
        List<Map<String, Object>> userList = dashboardService.getAverageCostPerUsersPerDiscipline();

        return userList.stream()
                .map(user -> DisciplineValueDto.builder()
                        .id((Integer) user.get("id"))
                        .name((String) user.get("discipline"))
                        .value((Long) user.get("avgCost"))
                        .build())
                .toList();
    }

    @Override
    public List<LicenseExpiringDto> getExpiredLicenses() {
        return dashboardService.getExpiringLicenses().stream()
                .map(licenseExpiringMapper::toLicenseExpiringDto)
                .toList();
    }

    @Override
    public List<LicenseUnusedDto> getUnusedLicenses() {
        return dashboardService.getUnusedLicenses().stream()
                .map(licenseUnusedMapper::toLicenseUnusedDto)
                .toList();
    }

    private Map<LocalDate, List<LicenseCost>> groupCostsByDate(List<LicenseCost> licenseCosts) {
        return licenseCosts
                .stream()
                .collect(Collectors.groupingBy(LicenseCost::getStartDate));
    }

    private void setDateToOne(LicenseCost licenseCost) {
        licenseCost.setStartDate(LocalDate.of(licenseCost.getStartDate().getYear(),
                licenseCost.getStartDate().getMonth(),
                1));
    }

    private void countCostPerMonth(LocalDate key, List<LicenseCost> value, Map<LocalDate, BigDecimal> mapToPut) {
        mapToPut.put(key, valueOf(value
                .stream()
                .map(LicenseCost::getLicenceCost)
                .mapToDouble(BigDecimal::doubleValue)
                .sum()));
    }

    private void convertToMonthDTO(LocalDate key, BigDecimal value, List<MonthDto> months) {
        months.add(MonthDto
                .builder()
                .month(key.getMonth().getValue())
                .year(key.getYear())
                .cost(value)
                .build());
    }

    private void addMissingMonths(List<MonthDto> months) {
        for (int i = 0; i < 12; i++) {
            MonthDto date = new MonthDto();
            date.setMonth(i + 1);

            if (i <= LocalDate.now().getMonth().getValue() - 1)
                date.setYear(LocalDate.now().getYear());
            else
                date.setYear(LocalDate.now().getYear() - 1);

            if (!months.contains(date)) {
                date.setCost(valueOf(0));
                months.add(date);
            }
        }
    }
}
