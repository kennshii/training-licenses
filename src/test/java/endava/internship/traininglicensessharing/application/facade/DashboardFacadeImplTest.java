package endava.internship.traininglicensessharing.application.facade;

import static endava.internship.traininglicensessharing.utils.DashboardTestUtils.AVERAGE_COSTS_DTO_STUB;
import static endava.internship.traininglicensessharing.utils.DashboardTestUtils.AVERAGE_COST_PER_USERS_PER_DISCIPLINE;
import static endava.internship.traininglicensessharing.utils.DashboardTestUtils.LICENSE_COST_LIST_FOR_12_MONTHS;
import static endava.internship.traininglicensessharing.utils.DashboardTestUtils.USERS_BY_DISCIPLINE_COUNT;
import static endava.internship.traininglicensessharing.utils.DashboardTestUtils.USERS_OVERVIEW_DTO_STUB;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE1;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE2;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE3;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE_EXPIRING_DTO1;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE_UNUSED_DTO2;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE_UNUSED_DTO3;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import endava.internship.traininglicensessharing.application.dto.AverageCostsDto;
import endava.internship.traininglicensessharing.application.dto.LicenseCostDto;
import endava.internship.traininglicensessharing.application.dto.LicenseExpiringDto;
import endava.internship.traininglicensessharing.application.dto.LicenseUnusedDto;
import endava.internship.traininglicensessharing.application.dto.MonthDto;
import endava.internship.traininglicensessharing.application.dto.UsersOverviewDto;
import endava.internship.traininglicensessharing.application.mapper.LicenseExpiringMapper;
import endava.internship.traininglicensessharing.application.mapper.LicenseUnusedMapper;
import endava.internship.traininglicensessharing.domain.entity.License;
import endava.internship.traininglicensessharing.domain.service.DashboardService;

@ExtendWith(MockitoExtension.class)
class DashboardFacadeImplTest {

    @InjectMocks
    private DashboardFacadeImpl dashboardFacade;

    @Mock
    private DashboardService dashboardService;

    @Mock
    private LicenseExpiringMapper licenseExpiringMapper;

    @Mock
    private LicenseUnusedMapper licenseUnusedMapper;

    @Test
    void getExpiredLicenses() {
        List<License> expiringLicenses = Collections.singletonList(LICENSE1);
        List<LicenseExpiringDto> expectedList = Collections.singletonList(LICENSE_EXPIRING_DTO1);

        when(dashboardService.getExpiringLicenses()).thenReturn(expiringLicenses);
        when(licenseExpiringMapper.toLicenseExpiringDto(any(License.class))).thenReturn(LICENSE_EXPIRING_DTO1);

        List<LicenseExpiringDto> actualList = dashboardFacade.getExpiredLicenses();

        assertEquals(expectedList.size(), actualList.size());
    }

    @Test
    void getUnusedLicenses() {
        List<License> unusedLicenses = Collections.singletonList(LICENSE2);
        List<LicenseUnusedDto> expectedList = Collections.singletonList(LICENSE_UNUSED_DTO2);

        when(dashboardService.getUnusedLicenses()).thenReturn(unusedLicenses);
        when(licenseUnusedMapper.toLicenseUnusedDto(any(License.class))).thenReturn(LICENSE_UNUSED_DTO2);

        List<LicenseUnusedDto> actualList = dashboardFacade.getUnusedLicenses();

        assertEquals(expectedList, actualList);
    }

    @Test
    void getUnusedLicensesWithYearDurationUnit() {
        List<License> unusedLicenses = Collections.singletonList(LICENSE3);
        List<LicenseUnusedDto> expectedList = Collections.singletonList(LICENSE_UNUSED_DTO3);

        when(dashboardService.getUnusedLicenses()).thenReturn(unusedLicenses);
        when(licenseUnusedMapper.toLicenseUnusedDto(any(License.class))).thenReturn(LICENSE_UNUSED_DTO3);

        List<LicenseUnusedDto> actualList = dashboardFacade.getUnusedLicenses();

        assertEquals(expectedList, actualList);
    }

    @Test
    void shouldCheckIfTheParametersWereReceivedCorrectly_whenCalledGetUsersOverview() {
        when(dashboardService.getTotalUsers()).thenReturn(USERS_OVERVIEW_DTO_STUB.getTotalUsers());
        when(dashboardService.getDeltaUsers()).thenReturn(USERS_OVERVIEW_DTO_STUB.getDeltaUsers());
        when(dashboardService.getTotalDisciplines()).thenReturn(USERS_OVERVIEW_DTO_STUB.getTotalDisciplines());
        when(dashboardService.getUsersByDisciplineCount()).thenReturn(USERS_BY_DISCIPLINE_COUNT);

        UsersOverviewDto usersOverviewDtoResult = dashboardFacade.getUsersOverview();

        assertAll(
                () -> assertEquals(USERS_OVERVIEW_DTO_STUB.getTotalUsers(), usersOverviewDtoResult.getTotalUsers()),
                () -> assertEquals(USERS_OVERVIEW_DTO_STUB.getDeltaUsers(), usersOverviewDtoResult.getDeltaUsers()),
                () -> assertEquals(USERS_OVERVIEW_DTO_STUB.getTotalDisciplines(), usersOverviewDtoResult.getTotalDisciplines()),
                () -> assertEquals(USERS_OVERVIEW_DTO_STUB.getNumberOfUsersPerDiscipline(), usersOverviewDtoResult.getNumberOfUsersPerDiscipline())
        );
    }

    @Test
    void shouldCheckIfTheParametersWereReceivedCorrectly_whenCalledGetAverageCostsDto() {
        when(dashboardService.getAverageCostPerUser()).thenReturn(AVERAGE_COSTS_DTO_STUB.getAverageCostPerUser());
        when(dashboardService.getAverageCostPerUsersPerDiscipline()).thenReturn(AVERAGE_COST_PER_USERS_PER_DISCIPLINE);

        AverageCostsDto averageCostsDtoResult = dashboardFacade.getAverageCostsDto();

        assertAll(
                () -> assertEquals(averageCostsDtoResult.getAverageCostPerUser(), AVERAGE_COSTS_DTO_STUB.getAverageCostPerUser()),
                () -> assertEquals(averageCostsDtoResult.getAverageCostPerDepartmentsMap(), AVERAGE_COSTS_DTO_STUB.getAverageCostPerDepartmentsMap())
        );
    }

    @Test
    void itShouldReturnCostsData() {
        final MonthDto MONTH_DTO = MonthDto.builder()
                .cost(valueOf(0))
                .month(8)
                .year(LocalDateTime.now().getYear() - 1)
                .build();

        when(dashboardService.getNumberOfTrainingsForCurrentYear()).thenReturn(2L);
        when(dashboardService.getDeltaForTrainings()).thenReturn(1L);
        when(dashboardService.getNumberOfSoftwareForCurrentYear()).thenReturn(2L);
        when(dashboardService.getDeltaForSoftware()).thenReturn(1L);
        when(dashboardService.getCostsForCurrentYear()).thenReturn(valueOf(2300));
        when(dashboardService.getDeltaForCosts()).thenReturn(valueOf(800));
        when(dashboardService.getCostsForLast12Months()).thenReturn(LICENSE_COST_LIST_FOR_12_MONTHS);

        LicenseCostDto licenseCostDTO = dashboardFacade.getCostsData();

        assertAll(
                () -> assertThat(licenseCostDTO.getDeltaCosts()).isEqualTo(valueOf(800)),
                () -> assertThat(licenseCostDTO.getCostsForCurrentYear()).isEqualTo(valueOf(2300)),
                () -> assertThat(licenseCostDTO.getDeltaSoftware()).isEqualTo(1),
                () -> assertThat(licenseCostDTO.getNumberOfSoftware()).isEqualTo(2),
                () -> assertThat(licenseCostDTO.getDeltaTrainings()).isEqualTo(1),
                () -> assertThat(licenseCostDTO.getNumberOfTrainings()).isEqualTo(2),
                () -> assertThat(licenseCostDTO.getCostsReport().get(0)).isEqualTo(MONTH_DTO)
        );
    }
}