package endava.internship.traininglicensessharing.rest.controller;

import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.COST;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.CREDENTIAL_DTO;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.CURRENCY;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.DESCRIPTION;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.EXPIRES_ON;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE_DTO_RESPONSE;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE_DURATION;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE_ID;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE_NAME;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE_TYPE;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE_WEBSITE;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.NOT_FOUND;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.SEATS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import endava.internship.traininglicensessharing.application.dto.LicenseDtoRequest;
import endava.internship.traininglicensessharing.application.dto.LicenseDtoResponse;
import endava.internship.traininglicensessharing.application.exception.ResourceNotFoundException;
import endava.internship.traininglicensessharing.application.exception.ValidationCustomException;
import endava.internship.traininglicensessharing.application.facade.LicenseFacade;

@WebMvcTest(controllers = {LicenseControllerImpl.class})
public class LicenseControllerTest {

    public static final String URI = "/licenses";

    public static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LicenseFacade licenseFacade;

    private final LicenseDtoRequest request = LicenseDtoRequest.builder()
            .name(LICENSE_NAME)
            .website(LICENSE_WEBSITE)
            .licenseType(LICENSE_TYPE)
            .description(DESCRIPTION)
            .cost(COST)
            .currency(CURRENCY)
            .expiresOn(EXPIRES_ON)
            .isRecurring(false)
            .seatsTotal(SEATS)
            .credentials(List.of(CREDENTIAL_DTO))
            .licenseDuration(LICENSE_DURATION)
            .build();

    @Test
    void itShouldCreateLicenseSuccessfully() throws Exception {
        String jsonLicenseRequest = objectMapper.writeValueAsString(request);
        String expectedResponse = objectMapper.writeValueAsString(LICENSE_DTO_RESPONSE);

        when(licenseFacade.addLicense(any(), any())).thenReturn(LICENSE_DTO_RESPONSE);

        String actualResponse = mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonLicenseRequest))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void itShouldUpdateLicenseSuccessfully() throws Exception {
        String jsonLicenseRequest = objectMapper.writeValueAsString(request);
        String expectedResponse = objectMapper.writeValueAsString(LICENSE_DTO_RESPONSE);

        when(licenseFacade.updateLicense(any(), any(), any())).thenReturn(LICENSE_DTO_RESPONSE);
        String actualResponse = mockMvc.perform(put(URI + "/" + LICENSE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonLicenseRequest))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void itShouldReturnBadRequestBecauseValidationsFailedUpdateMethod() throws Exception {
        request.setName("ad");
        request.setWebsite("adf");
        request.setDescription("test");
        request.setCredentials(null);
        String jsonLicense = objectMapper.writeValueAsString(request);

        Map<String, String> expectedLicense = new LinkedHashMap<>();
        expectedLicense.put("credentials", "must not be empty");
        expectedLicense.put("description", "Must be 5 to 250 alphanumeric characters, including special symbols.");
        expectedLicense.put("name", "Must be 3 to 20 alphanumeric characters, including special symbols.");
        expectedLicense.put("website", "Must be 5 to 30 alphanumeric characters, including special symbols.");

        when(licenseFacade.updateLicense(any(), any(), any())).thenThrow(new ValidationCustomException(expectedLicense));

        String actualResponse = mockMvc.perform(put(URI + "/" + LICENSE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonLicense))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();


        Map<String, Object> response = objectMapper.readValue(actualResponse, LinkedHashMap.class);

        assertThat(response).isEqualTo(expectedLicense);
    }

    @Test
    void itShouldReturnBadRequestBecauseValidationsFailed() throws Exception {
        request.setName("ad");
        request.setWebsite("adf");
        request.setDescription("test");
        request.setCredentials(null);
        String jsonLicense = objectMapper.writeValueAsString(request);

        Map<String, String> expectedLicense = new LinkedHashMap<>();
        expectedLicense.put("credentials", "must not be empty");
        expectedLicense.put("description", "Must be 5 to 250 alphanumeric characters, including special symbols.");
        expectedLicense.put("name", "Must be 3 to 20 alphanumeric characters, including special symbols.");
        expectedLicense.put("website", "Must be 5 to 30 alphanumeric characters, including special symbols.");

        when(licenseFacade.addLicense(any(), any())).thenThrow(new ValidationCustomException(expectedLicense));

        String actualResponse = mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonLicense))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();


        Map<String, Object> response = objectMapper.readValue(actualResponse, LinkedHashMap.class);

        assertThat(response).isEqualTo(expectedLicense);
    }

    @Test
    void itShouldReturnBadRequestLogoSize() throws Exception {
        request.setLogo(new Byte[]{1, 2, 3});
        String jsonLicense = objectMapper.writeValueAsString(request);

        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("logo", "Must be between 2MB and 10MB.");

        when(licenseFacade.addLicense(any(), any())).thenThrow(new ValidationCustomException(errors));

        String actualResponse = mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonLicense))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        assertThat(actualResponse).contains("\"Must be between 2MB and 10MB.\"");
    }

    @Test
    void itShouldGetAllLicenses() throws Exception {
        final List<LicenseDtoResponse> LICENSE_DTO_RESPONSEList = List.of(LICENSE_DTO_RESPONSE);

        when(licenseFacade.getAllLicences()).thenReturn(LICENSE_DTO_RESPONSEList);

        String actualResponse = mockMvc.perform(get(URI))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        final List<LicenseDtoResponse> list = objectMapper.readValue(actualResponse, new TypeReference<>() {
        });

        assertTrue(list.containsAll(LICENSE_DTO_RESPONSEList));
    }

    @Test
    void itShouldGetLicenseById() throws Exception {
        LICENSE_DTO_RESPONSE.setId(LICENSE_ID);
        when(licenseFacade.getLicenseById(LICENSE_ID)).thenReturn(LICENSE_DTO_RESPONSE);

        final String actualResponse = mockMvc.perform(get(URI + "/" + LICENSE_ID))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        LicenseDtoResponse licenseDtoResponse = objectMapper.readValue(actualResponse, LicenseDtoResponse.class);

        assertThat(licenseDtoResponse).isEqualTo(LICENSE_DTO_RESPONSE);
    }

    @Test
    void itShouldThrow_WhenGetLicenseWithUnknownId() throws Exception {
        when(licenseFacade.getLicenseById(LICENSE_ID)).thenThrow(new ResourceNotFoundException(NOT_FOUND));

        String actualResponse = mockMvc.perform(get(URI + "/" + LICENSE_ID))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        final Map<String, String> errors = new HashMap<>();
        errors.put("Not found", NOT_FOUND);

        final Map<String, String> actualErrors = objectMapper.readValue(actualResponse, HashMap.class);

        assertThat(actualErrors).isEqualTo(errors);
    }

    @Test
    void itShouldDeleteLicenseById() throws Exception {
        String idToDelete = "20";

        mockMvc.perform(delete(URI + "/" + idToDelete))
                .andExpect(status().isOk());

        verify(licenseFacade).deleteLicenseById(idToDelete);
    }
}