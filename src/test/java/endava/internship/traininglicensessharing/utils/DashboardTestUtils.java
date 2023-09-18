package endava.internship.traininglicensessharing.utils;

import static endava.internship.traininglicensessharing.domain.enums.LicenseType.SOFTWARE;
import static endava.internship.traininglicensessharing.domain.enums.LicenseType.TRAINING;
import static java.math.BigDecimal.valueOf;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import endava.internship.traininglicensessharing.application.dto.AverageCostsDto;
import endava.internship.traininglicensessharing.application.dto.DisciplineValueDto;
import endava.internship.traininglicensessharing.application.dto.LicenseCostDto;
import endava.internship.traininglicensessharing.application.dto.LicenseExpiringDto;
import endava.internship.traininglicensessharing.application.dto.LicenseUnusedDto;
import endava.internship.traininglicensessharing.application.dto.MonthDto;
import endava.internship.traininglicensessharing.application.dto.UsersOverviewDto;
import endava.internship.traininglicensessharing.application.mapper.LicenseExpiringMapper;
import endava.internship.traininglicensessharing.application.mapper.LicenseUnusedMapper;
import endava.internship.traininglicensessharing.domain.entity.Discipline;
import endava.internship.traininglicensessharing.domain.entity.License;
import endava.internship.traininglicensessharing.domain.entity.LicenseCost;
import endava.internship.traininglicensessharing.domain.entity.Request;
import endava.internship.traininglicensessharing.domain.entity.User;

public class DashboardTestUtils {

    private static final List<DisciplineValueDto> USERS_BY_DISCIPLINE_COUNT_MAP;
    static {
        USERS_BY_DISCIPLINE_COUNT_MAP = new ArrayList<>();
        USERS_BY_DISCIPLINE_COUNT_MAP.add(new DisciplineValueDto(1, "Discipline A", 20L));
        USERS_BY_DISCIPLINE_COUNT_MAP.add(new DisciplineValueDto(2, "Discipline B", 30L));
    }

    private static final List<DisciplineValueDto> AVERAGE_COSTS_PER_USER_PER_DISCIPLINE_MAP;
    static {
        AVERAGE_COSTS_PER_USER_PER_DISCIPLINE_MAP = new ArrayList<>();
        AVERAGE_COSTS_PER_USER_PER_DISCIPLINE_MAP.add(new DisciplineValueDto(1, "AM", 1450L));
        AVERAGE_COSTS_PER_USER_PER_DISCIPLINE_MAP.add(new DisciplineValueDto(2, "Development", 1200L));
        AVERAGE_COSTS_PER_USER_PER_DISCIPLINE_MAP.add(new DisciplineValueDto(3, "Testing", 590L));
    }

    public static final UsersOverviewDto USERS_OVERVIEW_DTO_STUB = UsersOverviewDto.builder()
            .numberOfUsersPerDiscipline(USERS_BY_DISCIPLINE_COUNT_MAP)
            .totalUsers(100L)
            .deltaUsers(10L)
            .totalDisciplines(5L)
            .build();

    public static final AverageCostsDto AVERAGE_COSTS_DTO_STUB = AverageCostsDto.builder()
            .averageCostPerDepartmentsMap(AVERAGE_COSTS_PER_USER_PER_DISCIPLINE_MAP)
            .averageCostPerUser(1950L)
            .build();

    public static UsersOverviewDto setUpExpectedUsersOverviewDto(List<User> userList, List<Discipline> disciplineList) {
        Map<String, Long> userCountByDepartment = userList.stream()
                .collect(Collectors.groupingBy(
                        user -> user.getWorkplace().getDiscipline().getName(),
                        Collectors.counting()
                ));

        Set<String> allDisciplines = disciplineList.stream()
                .map(Discipline::getName)
                .collect(Collectors.toSet());

        Map<String, Long> userCountByDepartmentMap = new LinkedHashMap<>();
        for (String discipline : allDisciplines) {
            userCountByDepartmentMap.put(discipline, userCountByDepartment.getOrDefault(discipline, 0L));
        }

        List<DisciplineValueDto> disciplineValueDtoList = userCountByDepartmentMap.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .map(entry -> new DisciplineValueDto(null, entry.getKey(), entry.getValue()))
                .toList();

        UsersOverviewDto expectedUsersOverviewDto = new UsersOverviewDto();
        expectedUsersOverviewDto.setTotalUsers((long) userList.size());
        expectedUsersOverviewDto.setDeltaUsers( userList.stream()
                .filter(user -> user.getRegistrationDate().getMonth() == LocalDate.now().getMonth())
                .count());
        expectedUsersOverviewDto.setTotalDisciplines((long) disciplineList.size());
        expectedUsersOverviewDto.setNumberOfUsersPerDiscipline(disciplineValueDtoList);

        return expectedUsersOverviewDto;
    }

    public static AverageCostsDto setUpExpectedAverageCostsDto(List<License> licenseList, List<User> userList, List<Discipline> disciplineList) {
        Map<String, Long> userCountByDepartment = userList.stream()
                .collect(Collectors.groupingBy(
                        user -> user.getWorkplace().getDiscipline().getName(),
                        Collectors.counting()
                ));

        long sumOfActiveTrainingLicenses = licenseList.stream()
                .filter(x ->
                    x.getExpiresOn().minusMonths(x.getLicenseDuration())
                        .getYear() == LocalDate.now().getYear()
                )
                .mapToLong(x -> x.getCost().longValue())
                .sum();
        long totalDiscipline = disciplineList.size();
        long avgCostPerDiscipline = sumOfActiveTrainingLicenses/totalDiscipline;
        long avgCostsPerUser = sumOfActiveTrainingLicenses / (userList.size());

        Set<String> allDisciplines = disciplineList.stream()
                .map(Discipline::getName)
                .collect(Collectors.toSet());

        Map<String, Long> avgCostPerUserPerDiscipline = new LinkedHashMap<>();
        for (String discipline : allDisciplines) {
            long countUsersPerDiscipline = userCountByDepartment.getOrDefault(discipline, 0L);
            long avgCostPerUsersPerDiscipline = countUsersPerDiscipline > 0 ? Math.round( (double) avgCostPerDiscipline / countUsersPerDiscipline ) : 0;
            avgCostPerUserPerDiscipline.put(discipline, avgCostPerUsersPerDiscipline);
        }

        List<DisciplineValueDto> sortedByCosts = avgCostPerUserPerDiscipline.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .map(entry -> new DisciplineValueDto(null, entry.getKey(), entry.getValue()))
                .toList();

        AverageCostsDto expectedAverageCostsDto = new AverageCostsDto();
        expectedAverageCostsDto.setAverageCostPerUser(avgCostsPerUser);
        expectedAverageCostsDto.setAverageCostPerDepartmentsMap(sortedByCosts);

        return expectedAverageCostsDto;
    }

    public static final Integer CURRENT_YEAR = LocalDateTime.now().getYear();

    public static final Integer PRECEDENT_YEAR = LocalDateTime.now().getYear() - 1;

    public static final List<MonthDto> LIST_WITH_MONTH_DTO = List.of(
            MonthDto.builder().month(8).year(PRECEDENT_YEAR).cost(valueOf(0)).build(),
            MonthDto.builder().month(9).year(PRECEDENT_YEAR).cost(valueOf(0)).build(),
            MonthDto.builder().month(10).year(PRECEDENT_YEAR).cost(valueOf(0)).build(),
            MonthDto.builder().month(11).year(PRECEDENT_YEAR).cost(valueOf(0)).build(),
            MonthDto.builder().month(12).year(PRECEDENT_YEAR).cost(valueOf(2300)).build(),
            MonthDto.builder().month(1).year(CURRENT_YEAR).cost(valueOf(2800)).build(),
            MonthDto.builder().month(2).year(CURRENT_YEAR).cost(valueOf(0)).build(),
            MonthDto.builder().month(3).year(CURRENT_YEAR).cost(valueOf(0)).build(),
            MonthDto.builder().month(4).year(CURRENT_YEAR).cost(valueOf(1800)).build(),
            MonthDto.builder().month(5).year(CURRENT_YEAR).cost(valueOf(0)).build(),
            MonthDto.builder().month(6).year(CURRENT_YEAR).cost(valueOf(0)).build(),
            MonthDto.builder().month(7).year(CURRENT_YEAR).cost(valueOf(0)).build()
    );

    public static final LicenseCostDto LICENSE_COST_DTO = LicenseCostDto.builder()
            .costsReport(LIST_WITH_MONTH_DTO)
            .deltaCosts(valueOf(-900).setScale(1))
            .costsForCurrentYear(valueOf(4600).setScale(1))
            .numberOfSoftware(2L)
            .deltaSoftware(0L)
            .numberOfTrainings(1L)
            .deltaTrainings(0L)
            .build();

    public static final List<LicenseCost> LICENSE_COST_LIST = List.of(
            LicenseCost.builder().licenceCost(valueOf(1500).setScale(2)).expireDate(LocalDate.of(CURRENT_YEAR, 4, 21)).licenseType(SOFTWARE).startDate(LocalDate.of(CURRENT_YEAR, 1, 20)).build(),
            LicenseCost.builder().licenceCost(valueOf(1200).setScale(2)).expireDate(LocalDate.of(PRECEDENT_YEAR, 5, 21)).licenseType(TRAINING).startDate(LocalDate.of(PRECEDENT_YEAR, 2, 12)).build(),
            LicenseCost.builder().licenceCost(valueOf(1300).setScale(2)).expireDate(LocalDate.of(CURRENT_YEAR, 8, 21)).licenseType(TRAINING).startDate(LocalDate.of(CURRENT_YEAR, 1, 21)).build(),
            LicenseCost.builder().licenceCost(valueOf(1800).setScale(2)).expireDate(LocalDate.of(CURRENT_YEAR, 9, 2)).licenseType(SOFTWARE).startDate(LocalDate.of(CURRENT_YEAR, 4, 2)).build(),
            LicenseCost.builder().licenceCost(valueOf(2000).setScale(2)).expireDate(LocalDate.of(PRECEDENT_YEAR, 5, 18)).licenseType(SOFTWARE).startDate(LocalDate.of(PRECEDENT_YEAR, 2, 5)).build(),
            LicenseCost.builder().licenceCost(valueOf(2300).setScale(2)).expireDate(LocalDate.of(PRECEDENT_YEAR, 12, 21)).licenseType(SOFTWARE).startDate(LocalDate.of(PRECEDENT_YEAR, 12, 21)).build()
    );

    public static final List<LicenseCost> LICENSE_COST_LIST_FOR_12_MONTHS = List.of(
            LicenseCost.builder().licenceCost(valueOf(1500).setScale(2)).expireDate(LocalDate.of(CURRENT_YEAR, 4, 21)).licenseType(SOFTWARE).startDate(LocalDate.of(CURRENT_YEAR, 1, 20)).build(),
            LicenseCost.builder().licenceCost(valueOf(1200).setScale(2)).expireDate(LocalDate.of(PRECEDENT_YEAR, 12, 21)).licenseType(TRAINING).startDate(LocalDate.of(PRECEDENT_YEAR, 9, 12)).build(),
            LicenseCost.builder().licenceCost(valueOf(1300).setScale(2)).expireDate(LocalDate.of(CURRENT_YEAR, 8, 21)).licenseType(TRAINING).startDate(LocalDate.of(CURRENT_YEAR, 1, 21)).build(),
            LicenseCost.builder().licenceCost(valueOf(1800).setScale(2)).expireDate(LocalDate.of(CURRENT_YEAR, 9, 2)).licenseType(SOFTWARE).startDate(LocalDate.of(CURRENT_YEAR, 4, 2)).build(),
            LicenseCost.builder().licenceCost(valueOf(2000).setScale(2)).expireDate(LocalDate.of(PRECEDENT_YEAR, 12, 18)).licenseType(SOFTWARE).startDate(LocalDate.of(PRECEDENT_YEAR, 9, 5)).build(),
            LicenseCost.builder().licenceCost(valueOf(2300).setScale(2)).expireDate(LocalDate.of(PRECEDENT_YEAR, 12, 21)).licenseType(SOFTWARE).startDate(LocalDate.of(PRECEDENT_YEAR, 12, 21)).build()
    );

    public static List<LicenseExpiringDto> getExpectedListOfLicenseExpiringDto(List<License> expectedLicenses, LicenseExpiringMapper licenseExpiringMapper) {
         return expectedLicenses.stream()
                 .filter(x -> x.getExpiresOn().isAfter(LocalDate.now()))
                 .sorted(Comparator.comparing(License::getExpiresOn))
                 .map(licenseExpiringMapper::toLicenseExpiringDto)
                 .toList();
    }

    public static List<LicenseUnusedDto> getExpectedListOfLicenseUnusedDto(List<License> expectedLicenses, List<Request> expectedRequests, LicenseUnusedMapper licenseUnusedMapper) {
        return expectedLicenses.stream()
                .filter(license -> expectedRequests.stream()
                        .noneMatch(request -> request.getLicense().equals(license)))
                .map(licenseUnusedMapper::toLicenseUnusedDto)
                .toList();
    }

    public static  List<Map<String, Object>> AVERAGE_COST_PER_USERS_PER_DISCIPLINE =
            getAverageCostPerUsersPerDiscipline(AVERAGE_COSTS_DTO_STUB.getAverageCostPerDepartmentsMap());

    public static List<Map<String, Object>> getAverageCostPerUsersPerDiscipline(List<DisciplineValueDto> userList) {
        return userList.stream()
                .map(dto ->
                        Map.of("id", (Object) dto.getId(),
                                "discipline", dto.getName(),
                                "avgCost", dto.getValue()
                        ))
                .toList();
    }

    public static List<Map<String, Object>> USERS_BY_DISCIPLINE_COUNT = getUsersByDisciplineCount(USERS_BY_DISCIPLINE_COUNT_MAP);
    public static List<Map<String, Object>> getUsersByDisciplineCount(List<DisciplineValueDto> userList) {
        return userList.stream()
                .map(dto ->
                        Map.of("id", (Object) dto.getId(),
                                "discipline", dto.getName(),
                                "userNum", dto.getValue()
                        ))
                .toList();
    }
}
