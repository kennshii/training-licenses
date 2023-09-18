package endava.internship.traininglicensessharing.rest.controller;

import static endava.internship.traininglicensessharing.rest.controller.BaseController.createExpectedBody;
import static endava.internship.traininglicensessharing.rest.controller.LicenseControllerTest.objectMapper;
import static endava.internship.traininglicensessharing.utils.DashboardTestUtils.AVERAGE_COSTS_DTO_STUB;
import static endava.internship.traininglicensessharing.utils.DashboardTestUtils.LICENSE_COST_DTO;
import static endava.internship.traininglicensessharing.utils.DashboardTestUtils.USERS_OVERVIEW_DTO_STUB;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE_EXPIRING_DTO1;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE_UNUSED_DTO2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import endava.internship.traininglicensessharing.application.facade.DashboardFacade;
import endava.internship.traininglicensessharing.application.dto.AverageCostsDto;
import endava.internship.traininglicensessharing.application.dto.LicenseCostDto;
import endava.internship.traininglicensessharing.application.dto.LicenseExpiringDto;
import endava.internship.traininglicensessharing.application.dto.LicenseUnusedDto;
import endava.internship.traininglicensessharing.application.dto.UsersOverviewDto;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DashboardControllerImpl.class)
class DashboardControllerTest {

    public static final String BASE_URL = "/dashboard";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private DashboardFacade dashboardFacade;

    @Test
    void getExpiringLicenses() throws Exception {
        List<LicenseExpiringDto> expiringLicenses = Collections.singletonList(LICENSE_EXPIRING_DTO1);
        when(dashboardFacade.getExpiredLicenses()).thenReturn(expiringLicenses);

        mockMvc.perform(get(BASE_URL + "/licenses/expiring"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(createExpectedBody(expiringLicenses)));
    }

    @Test
    void getUnusedLicenses() throws Exception {
        List<LicenseUnusedDto> unusedLicenses = Collections.singletonList(LICENSE_UNUSED_DTO2);
        when(dashboardFacade.getUnusedLicenses()).thenReturn(unusedLicenses);

        mockMvc.perform(get(BASE_URL + "/licenses/unused"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(createExpectedBody(unusedLicenses)));
    }

    @Test
    void shouldCheckIfTheUsersOverviewDtoWasReceivedCorrectly_whenCalledGetUsersOverview() throws Exception {
        when(dashboardFacade.getUsersOverview()).thenReturn(USERS_OVERVIEW_DTO_STUB);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/user"))
                .andExpect(status().isOk())
                .andReturn();
        UsersOverviewDto actualDto = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        assertAll(
                () -> assertEquals(USERS_OVERVIEW_DTO_STUB.getTotalUsers(), actualDto.getTotalUsers()),
                () -> assertEquals(USERS_OVERVIEW_DTO_STUB.getDeltaUsers(), actualDto.getDeltaUsers()),
                () -> assertEquals(USERS_OVERVIEW_DTO_STUB.getTotalDisciplines(), actualDto.getTotalDisciplines()),
                () -> assertEquals(USERS_OVERVIEW_DTO_STUB.getNumberOfUsersPerDiscipline(), actualDto.getNumberOfUsersPerDiscipline())
        );
    }

    @Test
    void shouldCheckIfTheAverageCostsDtoWasReceivedCorrectly_whenCalledGetUsersOverview() throws Exception {
        when(dashboardFacade.getAverageCostsDto()).thenReturn(AVERAGE_COSTS_DTO_STUB);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/user/average"))
                .andExpect(status().isOk())
                .andReturn();
        AverageCostsDto actualDto = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        assertAll(
                () -> assertEquals(AVERAGE_COSTS_DTO_STUB.getAverageCostPerUser(), actualDto.getAverageCostPerUser()),
                () -> assertEquals(AVERAGE_COSTS_DTO_STUB.getAverageCostPerDepartmentsMap(), actualDto.getAverageCostPerDepartmentsMap())
        );
    }

    @Test
    void itShouldReturnCostsReportMonthsAsJson() throws Exception {
        when(dashboardFacade.getCostsData()).thenReturn(LICENSE_COST_DTO);

        mockMvc.perform(get(BASE_URL + "/costs/licenseCost"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
    }

    @Test
    void itShouldReturnCostsReportMonths() throws Exception {
        when(dashboardFacade.getCostsData()).thenReturn(LICENSE_COST_DTO);

        String returnedJson = mockMvc.perform(get(BASE_URL + "/costs/licenseCost")).andReturn().getResponse().getContentAsString();
        LicenseCostDto returned = objectMapper.readValue(returnedJson, LicenseCostDto.class);

        assertThat(returned).isEqualTo(LICENSE_COST_DTO);
    }

}