package endava.internship.traininglicensessharing.application.facade;

import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.CREDENTIAL_DTO;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE1;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE_DTO_REQUEST;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE_DTO_RESPONSE;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE_ID;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.NOT_FOUND;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;

import endava.internship.traininglicensessharing.application.mapper.LicenseMapper;
import endava.internship.traininglicensessharing.application.validator.LicenseValidator;
import endava.internship.traininglicensessharing.domain.entity.License;
import endava.internship.traininglicensessharing.domain.service.LicenseService;
import endava.internship.traininglicensessharing.application.exception.ResourceNotFoundException;
import endava.internship.traininglicensessharing.application.dto.LicenseDtoResponse;


@ExtendWith(MockitoExtension.class)
class LicenseFacadeImplTest {

    @InjectMocks
    LicenseFacadeImpl licenseFacade;

    @Mock
    private LicenseService licenseService;

    @Mock
    private BindingResult bindingResult;
    @Mock
    private LicenseValidator licenseValidator;
    @Mock
    private LicenseMapper licenseMapper;

    @Test
    void itShouldGetAllLicenses() {
        final List<License> LICENSE_LIST = List.of(LICENSE);

        when(licenseService.getAllLicenses()).thenReturn(LICENSE_LIST);
        when(licenseMapper.licenseToLicenseDtoResponse(LICENSE)).thenReturn(LICENSE_DTO_RESPONSE);

        List<LicenseDtoResponse> allLicences = licenseFacade.getAllLicences();

        assertAll(
                () -> assertTrue(allLicences.contains(LICENSE_DTO_RESPONSE)),
                () -> assertThat(allLicences.get(0)).isEqualTo(LICENSE_DTO_RESPONSE),
                () -> assertTrue(allLicences.get(0).getCredentials().contains(CREDENTIAL_DTO))
        );
    }

    @Test
    void itShouldAddLicense() {
        when(licenseService.addLicense(LICENSE)).thenReturn(LICENSE);
        when(licenseMapper.licenseToLicenseDtoResponse(LICENSE)).thenReturn(LICENSE_DTO_RESPONSE);
        when(licenseMapper.licenseDtoRequestToLicense(LICENSE_DTO_REQUEST)).thenReturn(LICENSE);
        when(bindingResult.hasErrors()).thenReturn(false);

        LicenseDtoResponse actualLicense = licenseFacade.addLicense(LICENSE_DTO_REQUEST, bindingResult);

        assertThat(actualLicense).isEqualTo(LICENSE_DTO_RESPONSE);
    }

    @Test
    void itShouldUpdateLicense() {
        when(licenseService.updateLicense(LICENSE, LICENSE1)).thenReturn(LICENSE);
        when(licenseService.getLicenseById(LICENSE_ID)).thenReturn(Optional.ofNullable(LICENSE1));
        when(licenseMapper.licenseDtoRequestToLicense(LICENSE_DTO_REQUEST)).thenReturn(LICENSE);
        when(licenseMapper.licenseToLicenseDtoResponse(LICENSE)).thenReturn(LICENSE_DTO_RESPONSE);
        when(bindingResult.hasErrors()).thenReturn(false);

        LicenseDtoResponse actualLicense = licenseFacade.updateLicense(LICENSE_DTO_REQUEST, LICENSE_ID, bindingResult);

        assertThat(actualLicense).isEqualTo(LICENSE_DTO_RESPONSE);
    }


    @Test
    void itShouldGetLicenseById() {
        when(licenseService.getLicenseById(LICENSE_ID)).thenReturn(Optional.of(LICENSE));
        when(licenseMapper.licenseToLicenseDtoResponse(LICENSE)).thenReturn(LICENSE_DTO_RESPONSE);

        LicenseDtoResponse actualResponse = licenseFacade.getLicenseById(LICENSE_ID);

        assertThat(actualResponse).isEqualTo(LICENSE_DTO_RESPONSE);
    }

    @Test
    void itShouldThrow_WhenGetLicenseWithUnknownId() {
        when(licenseService.getLicenseById(LICENSE_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> licenseFacade.getLicenseById(LICENSE_ID))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(NOT_FOUND);
    }

    @Test
    void itShouldDeleteLicense() {
        String idToDelete = "1";
        licenseFacade.deleteLicenseById(idToDelete);
        verify(licenseService).deleteById(Integer.parseInt(idToDelete));
    }
}