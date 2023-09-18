package endava.internship.traininglicensessharing.domain.service;

import static endava.internship.traininglicensessharing.domain.cache.CacheContext.AVERAGE_COSTS_OVERVIEW_CACHE;
import static endava.internship.traininglicensessharing.domain.cache.CacheContext.COST_DATA_OVERVIEW_CACHE;
import static endava.internship.traininglicensessharing.domain.cache.CacheContext.LICENSES_CACHE;
import static endava.internship.traininglicensessharing.domain.cache.CacheContext.USERS_OVERVIEW_CACHE;
import static java.math.BigDecimal.valueOf;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import endava.internship.traininglicensessharing.domain.entity.License;
import endava.internship.traininglicensessharing.domain.entity.LicenseCost;
import endava.internship.traininglicensessharing.domain.enums.LicenseType;
import endava.internship.traininglicensessharing.domain.repository.DisciplineRepository;
import endava.internship.traininglicensessharing.domain.repository.LicenseCostRepository;
import endava.internship.traininglicensessharing.domain.repository.LicenseRepository;
import endava.internship.traininglicensessharing.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@CacheConfig(keyGenerator = "cacheKeyGenerator")
public class DashboardServiceImpl implements DashboardService {

    private final DisciplineRepository disciplineRepository;

    private final UserRepository userRepository;

    private final LicenseRepository licenseRepository;

    private final LicenseCostRepository licenseCostRepository;

    @Cacheable(value = LICENSES_CACHE)
    @Override
    public List<License> getExpiringLicenses() {
        return licenseRepository.getExpiringLicenses();
    }

    @Cacheable(value = LICENSES_CACHE)
    @Override
    public List<License> getUnusedLicenses() {
        return licenseRepository.getUnusedLicenses();
    }

    @Cacheable(value = USERS_OVERVIEW_CACHE)
    @Override
    public Long getTotalUsers() {
        return userRepository.getTotalCountOfUsers();
    }

    @Cacheable(value = USERS_OVERVIEW_CACHE)
    @Override
    public Long getDeltaUsers() {
        return userRepository.getCountOfNewUsers();
    }

    @Cacheable(value = USERS_OVERVIEW_CACHE)
    @Override
    public Long getTotalDisciplines() {
        return disciplineRepository.count();
    }

    @Cacheable(value = USERS_OVERVIEW_CACHE)
    @Override
    public List<Map<String, Object>> getUsersByDisciplineCount() {
        return userRepository.getUsersPerDepartment();
    }

    @Cacheable(value = AVERAGE_COSTS_OVERVIEW_CACHE)
    @Override
    public Long getAverageCostPerUser() {
        long totalCostLicenses = licenseRepository.getTotalSumOfLicenses();
        long totalNumUsers = userRepository.getTotalCountOfUsers();

        return totalNumUsers > 0 ? Math.round( (double)  totalCostLicenses / totalNumUsers ) : totalCostLicenses;
    }

    @Cacheable(value = AVERAGE_COSTS_OVERVIEW_CACHE)
    @Override
    public List<Map<String, Object>> getAverageCostPerUsersPerDiscipline() {
        List<Map<String, Object>> usersByDisciplineCount = userRepository.getUsersPerDepartment();
        long totalCostLicenses = licenseRepository.getTotalSumOfLicenses();
        long disciplineCount = disciplineRepository.count();
        long averageCostPerDiscipline = totalCostLicenses / disciplineCount;
        String disciplineColumn = "discipline";

        return usersByDisciplineCount.stream()
                .map(usersPerDiscipline -> {
                    long avgCostPerUsersPerDiscipline = ((Long) usersPerDiscipline.get("userNum")) > 0 ? Math.round( (double) averageCostPerDiscipline / ((Long) usersPerDiscipline.get("userNum"))) : 0;
                    return Map.of("id", usersPerDiscipline.get("id"),
                            disciplineColumn, usersPerDiscipline.get(disciplineColumn),
                            "avgCost", avgCostPerUsersPerDiscipline);
                })
                .sorted(Comparator.comparingLong(m -> (Long) ((Map<String, Object>)m).get("avgCost")).reversed()
                        .thenComparing(m -> (String) ((Map<String, Object>)m).get(disciplineColumn)))
                .toList();
    }

    @Cacheable(value = COST_DATA_OVERVIEW_CACHE)
    @Override
    public BigDecimal getCostsForPreviousYear() {

        return valueOf(licenseCostRepository.getCostsForPreviousYear()
                .stream()
                .map(LicenseCost::getLicenceCost)
                .mapToDouble(BigDecimal::doubleValue)
                .sum());
    }

    @Cacheable(value = COST_DATA_OVERVIEW_CACHE)
    @Override
    public BigDecimal getCostsForCurrentYear() {

        return valueOf(licenseCostRepository.getCostsForCurrentYear()
                .stream()
                .map(LicenseCost::getLicenceCost)
                .mapToDouble(BigDecimal::doubleValue)
                .sum());
    }

    @Cacheable(value = COST_DATA_OVERVIEW_CACHE)
    @Override
    public List<LicenseCost> getCostsForLast12Months() {
        return licenseCostRepository.getCostsForLast12Months();
    }

    @Cacheable(value = COST_DATA_OVERVIEW_CACHE)
    @Override
    public Long getNumberOfTrainingsForCurrentYear() {

        return licenseCostRepository.getCostsForCurrentYear()
                .stream()
                .filter(licenseCost -> licenseCost.getLicenseType().equals(LicenseType.TRAINING))
                .count();
    }

    @Cacheable(value = COST_DATA_OVERVIEW_CACHE)
    @Override
    public Long getNumberOfTrainingsForPreviousYear() {

        return licenseCostRepository.getCostsForPreviousYear()
                .stream()
                .filter(licenseCost -> licenseCost.getLicenseType().equals(LicenseType.TRAINING))
                .count();
    }

    @Cacheable(value = COST_DATA_OVERVIEW_CACHE)
    @Override
    public Long getDeltaForTrainings() {
        return getNumberOfTrainingsForCurrentYear() - getNumberOfTrainingsForPreviousYear();
    }

    @Cacheable(value = COST_DATA_OVERVIEW_CACHE)
    @Override
    public Long getNumberOfSoftwareForCurrentYear() {

        return licenseCostRepository.getCostsForCurrentYear()
                .stream()
                .filter(licenseCost -> licenseCost.getLicenseType().equals(LicenseType.SOFTWARE))
                .count();
    }

    @Cacheable(value = COST_DATA_OVERVIEW_CACHE)
    @Override
    public Long getNumberOfSoftwareForPreviousYear() {

        return licenseCostRepository.getCostsForPreviousYear()
                .stream()
                .filter(licenseCost -> licenseCost.getLicenseType().equals(LicenseType.SOFTWARE))
                .count();
    }

    @Cacheable(value = COST_DATA_OVERVIEW_CACHE)
    @Override
    public Long getDeltaForSoftware() {
        return getNumberOfSoftwareForCurrentYear() - getNumberOfSoftwareForPreviousYear();
    }

    @Cacheable(value = COST_DATA_OVERVIEW_CACHE)
    @Override
    public BigDecimal getDeltaForCosts() {
        return getCostsForCurrentYear().subtract(getCostsForPreviousYear());
    }
}
