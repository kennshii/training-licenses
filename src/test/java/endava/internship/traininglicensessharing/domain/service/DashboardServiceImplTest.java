package endava.internship.traininglicensessharing.domain.service;

import static endava.internship.traininglicensessharing.utils.DashboardTestUtils.LICENSE_COST_LIST;
import static endava.internship.traininglicensessharing.utils.DashboardTestUtils.LICENSE_COST_LIST_FOR_12_MONTHS;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE1;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE2;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import endava.internship.traininglicensessharing.domain.entity.License;
import endava.internship.traininglicensessharing.domain.repository.DisciplineRepository;
import endava.internship.traininglicensessharing.domain.repository.LicenseCostRepository;
import endava.internship.traininglicensessharing.domain.repository.LicenseRepository;
import endava.internship.traininglicensessharing.domain.repository.UserRepository;

@SpringBootTest
class DashboardServiceImplTest {

    @Mock
    private DisciplineRepository disciplineRepository;

    @Mock
    private LicenseCostRepository licenseCostRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LicenseRepository licenseRepository;

    @InjectMocks
    private DashboardServiceImpl dashboardService;

    @Test
    void getExpiringLicenses() {
        List<License> expectedLicenses = Collections.singletonList(LICENSE1);
        when(licenseRepository.getExpiringLicenses()).thenReturn(expectedLicenses);

        List<License> actualLicenses = dashboardService.getExpiringLicenses();

        assertEquals(expectedLicenses, actualLicenses);
        verify(licenseRepository).getExpiringLicenses();
    }

    @Test
    void getUnusedLicenses() {
        List<License> expectedLicenses = Collections.singletonList(LICENSE2);
        when(licenseRepository.getUnusedLicenses()).thenReturn(expectedLicenses);

        List<License> actualLicenses = dashboardService.getUnusedLicenses();

        assertEquals(expectedLicenses, actualLicenses);
        verify(licenseRepository).getUnusedLicenses();
    }


    @Test
    void shouldReturnCorrectlyNumberOfTotalUsers_whenCalledGetTotalUsers() {
        Long totalUsersStub = 10L;

        when(userRepository.getTotalCountOfUsers()).thenReturn(totalUsersStub);

        Long totalUsersResult = dashboardService.getTotalUsers();

        assertEquals(totalUsersStub, totalUsersResult);
    }

    @Test
    void shouldReturnCorrectlyDeltaUsers_whenCalledGetDeltaUsers() {
        Long deltaUsersStub = 4L;

        when(userRepository.getCountOfNewUsers()).thenReturn(deltaUsersStub);

        Long deltaUsersResult = dashboardService.getDeltaUsers();

        assertEquals(deltaUsersStub, deltaUsersResult);
    }

    @Test
    void shouldReturnCorrectlyNumberOfDisciplines_whenCalledGetTotalDisciplines() {
        Long totalDisciplinesStub = 12L;

        when(disciplineRepository.count()).thenReturn(totalDisciplinesStub);

        Long totalDisciplinesResult = dashboardService.getTotalDisciplines();

        assertEquals(totalDisciplinesStub, totalDisciplinesResult);
    }

    @Test
    void shouldReturnCorrectlySortedLinkedHashMap_whenCalledGetUsersByDisciplineCount() {
        List<Map<String, Object>> userCountList = new ArrayList<>();

        Map<String, Object> disciplineCount1 = new LinkedHashMap<>();
        disciplineCount1.put("discipline", "Discipline A");
        disciplineCount1.put("userNum", 10L);
        userCountList.add(disciplineCount1);

        Map<String, Object> disciplineCount2 = new LinkedHashMap<>();
        disciplineCount2.put("discipline", "Discipline B");
        disciplineCount2.put("userNum", 20L);
        userCountList.add(disciplineCount2);

        when(userRepository.getUsersPerDepartment()).thenReturn(userCountList);

        List<Map<String, Object>> result = dashboardService.getUsersByDisciplineCount();

        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertEquals("Discipline A", result.get(0).get("discipline")),
                () -> assertEquals(10L, (long) result.get(0).get("userNum")),
                () -> assertEquals("Discipline B", result.get(1).get("discipline")),
                () -> assertEquals(20L, (long) result.get(1).get("userNum"))
        );
    }

    @Test
    void shouldReturnCorrectlyAverageCostPerUser_whenCalledGetAverageCostPerUser() {
        Long totalCostLicenses = 1000L;
        Long totalNumUsers = 50L;
        Long expectedAverageCostPerUser = totalCostLicenses / totalNumUsers;

        when(licenseRepository.getTotalSumOfLicenses()).thenReturn(totalCostLicenses);
        when(userRepository.getTotalCountOfUsers()).thenReturn(totalNumUsers);

        Long result = dashboardService.getAverageCostPerUser();

        assertEquals(expectedAverageCostPerUser, result);
    }

    @Test
    void shouldReturnTotalCosts_whenCalledGetAverageCostPerUserForNoUsersInRepository() {
        Long totalCostLicenses = 1000L;
        Long totalNumUsers = 0L;

        when(licenseRepository.getTotalSumOfLicenses()).thenReturn(totalCostLicenses);
        when(userRepository.getTotalCountOfUsers()).thenReturn(totalNumUsers);

        Long result = dashboardService.getAverageCostPerUser();

        assertEquals(totalCostLicenses, (long) result);
    }

    @Test
    void shouldReturnCorrectlySortedLinkedHashMap_whenCalledGetAverageCostPerUsersPerDiscipline() {
        List<Map<String, Object>> userCountList = new ArrayList<>();

        Map<String, Object> disciplineCount1 = new LinkedHashMap<>();
        disciplineCount1.put("id", 1L);
        disciplineCount1.put("discipline", "Discipline A");
        disciplineCount1.put("userNum", 10L);
        userCountList.add(disciplineCount1);

        Map<String, Object> disciplineCount2 = new LinkedHashMap<>();
        disciplineCount2.put("id", 2L);
        disciplineCount2.put("discipline", "Discipline B");
        disciplineCount2.put("userNum", 20L);
        userCountList.add(disciplineCount2);

        Map<String, Object> disciplineCount3 = new LinkedHashMap<>();
        disciplineCount3.put("id", 3L);
        disciplineCount3.put("discipline", "Discipline C");
        disciplineCount3.put("userNum", 3L);
        userCountList.add(disciplineCount3);

        Long disciplineCountStub = 3L;
        Long totalSumOfLicensesStub = 900L;

        when(userRepository.getUsersPerDepartment()).thenReturn(userCountList);
        when(disciplineRepository.count()).thenReturn(disciplineCountStub);
        when(licenseRepository.getTotalSumOfLicenses()).thenReturn(totalSumOfLicensesStub);

        List<Map<String, Object>> result = dashboardService.getAverageCostPerUsersPerDiscipline();

        assertAll(
                () -> assertEquals(3, result.size()),
                () -> assertEquals("Discipline C", result.get(0).get("discipline")),
                () -> assertEquals(100L, (long) result.get(0).get("avgCost")),
                () -> assertEquals("Discipline A", result.get(1).get("discipline")),
                () -> assertEquals(30L, (long) result.get(1).get("avgCost")),
                () -> assertEquals("Discipline B", result.get(2).get("discipline")),
                () -> assertEquals(15L, (long)  result.get(2).get("avgCost"))
        );
    }

    @Test
    void itShouldReturnCostsForPreviousYear() {
        when(licenseCostRepository.getCostsForPreviousYear()).thenReturn(List.of(LICENSE_COST_LIST.get(1), LICENSE_COST_LIST.get(4), LICENSE_COST_LIST.get(5)));

        assertThat(dashboardService.getCostsForPreviousYear()).isEqualTo(valueOf(5500.0));
    }

    @Test
    void itShouldReturnCostsForCurrentYear() {
        when(licenseCostRepository.getCostsForCurrentYear()).thenReturn(List.of(LICENSE_COST_LIST.get(0), LICENSE_COST_LIST.get(2), LICENSE_COST_LIST.get(3)));

        assertThat(dashboardService.getCostsForCurrentYear()).isEqualTo(valueOf(4600.0));
    }

    @Test
    void itShouldReturnCostsForLast12Months() {
        when(licenseCostRepository.getCostsForLast12Months()).thenReturn(LICENSE_COST_LIST_FOR_12_MONTHS);
        System.out.println(dashboardService.getCostsForLast12Months());

        assertThat(dashboardService.getCostsForLast12Months()).hasSize(6).hasSameElementsAs(LICENSE_COST_LIST_FOR_12_MONTHS);
    }

    @Test
    void itShouldReturnNumberOfTrainingsForCurrentYear() {
        when(licenseCostRepository.getCostsForCurrentYear()).thenReturn(List.of(LICENSE_COST_LIST.get(0), LICENSE_COST_LIST.get(2), LICENSE_COST_LIST.get(3)));

        assertThat(dashboardService.getNumberOfTrainingsForCurrentYear()).isEqualTo(1);
    }

    @Test
    void itShouldReturnNumberOfTrainingsForPreviousYear() {
        when(licenseCostRepository.getCostsForPreviousYear()).thenReturn(List.of(LICENSE_COST_LIST.get(1), LICENSE_COST_LIST.get(4), LICENSE_COST_LIST.get(5)));

        assertThat(dashboardService.getNumberOfTrainingsForPreviousYear()).isEqualTo(1);
    }

    @Test
    void itShouldReturnDeltaForTrainings() {
        when(licenseCostRepository.getCostsForPreviousYear()).thenReturn(List.of(LICENSE_COST_LIST.get(1), LICENSE_COST_LIST.get(4), LICENSE_COST_LIST.get(5)));
        when(licenseCostRepository.getCostsForCurrentYear()).thenReturn(List.of(LICENSE_COST_LIST.get(0), LICENSE_COST_LIST.get(2), LICENSE_COST_LIST.get(3)));

        assertThat(dashboardService.getDeltaForTrainings()).isZero();
    }

    @Test
    void itShouldReturnNumberOfSoftwareForCurrentYear() {
        when(licenseCostRepository.getCostsForCurrentYear()).thenReturn(List.of(LICENSE_COST_LIST.get(0), LICENSE_COST_LIST.get(2), LICENSE_COST_LIST.get(3)));

        assertThat(dashboardService.getNumberOfSoftwareForCurrentYear()).isEqualTo(2);
    }

    @Test
    void itShouldReturnNumberOfSoftwareForPreviousYear() {
        when(licenseCostRepository.getCostsForPreviousYear()).thenReturn(List.of(LICENSE_COST_LIST.get(1), LICENSE_COST_LIST.get(4), LICENSE_COST_LIST.get(5)));

        assertThat(dashboardService.getNumberOfSoftwareForPreviousYear()).isEqualTo(2);
    }

    @Test
    void itShouldReturnDeltaForSoftware() {
        when(licenseCostRepository.getCostsForPreviousYear()).thenReturn(List.of(LICENSE_COST_LIST.get(1), LICENSE_COST_LIST.get(4), LICENSE_COST_LIST.get(5)));
        when(licenseCostRepository.getCostsForCurrentYear()).thenReturn(List.of(LICENSE_COST_LIST.get(0), LICENSE_COST_LIST.get(2), LICENSE_COST_LIST.get(3)));

        assertThat(dashboardService.getDeltaForSoftware()).isZero();
    }

    @Test
    void itShouldReturnDeltaForCosts() {
        when(licenseCostRepository.getCostsForPreviousYear()).thenReturn(List.of(LICENSE_COST_LIST.get(1), LICENSE_COST_LIST.get(4), LICENSE_COST_LIST.get(5)));
        when(licenseCostRepository.getCostsForCurrentYear()).thenReturn(List.of(LICENSE_COST_LIST.get(0), LICENSE_COST_LIST.get(2), LICENSE_COST_LIST.get(3)));

        assertThat(dashboardService.getDeltaForCosts()).isEqualTo(valueOf(-900.0));
    }
}