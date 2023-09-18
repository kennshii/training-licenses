package endava.internship.traininglicensessharing.application.validator;

import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.CREDENTIAL;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.CREDENTIAL_DTO;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE_DTO_REQUEST;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE_ID;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE_ID2;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;

import endava.internship.traininglicensessharing.application.dto.LicenseDtoRequest;
import endava.internship.traininglicensessharing.domain.entity.Credential;
import endava.internship.traininglicensessharing.domain.entity.License;
import endava.internship.traininglicensessharing.domain.service.LicenseService;

@ExtendWith(MockitoExtension.class)
class LicenseValidatorTest {
    @Mock
    private LicenseService licenseService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private LicenseValidator licenseValidator;

    @Test
    void itShouldValidateName() {
        LicenseDtoRequest licenseDtoRequest = LICENSE_DTO_REQUEST;

        when(licenseService.getLicenseByName(licenseDtoRequest.getName())).thenReturn(Optional.empty());

        licenseValidator.validate(licenseDtoRequest, LICENSE_ID, bindingResult);

        verify(bindingResult, never()).rejectValue(eq("name"), anyString(), anyString());
    }

    @Test
    void itShouldNotValidateName() {
        LicenseDtoRequest licenseDtoRequest = LICENSE_DTO_REQUEST;

        when(licenseService.getLicenseByName(licenseDtoRequest.getName())).thenReturn(Optional.of(LICENSE));

        licenseValidator.validate(licenseDtoRequest, LICENSE_ID, bindingResult);

        verify(bindingResult).rejectValue("name", "", "Name must be unique");
    }

    @Test
    void itShouldValidateWebsite() {
        LicenseDtoRequest licenseDtoRequest = LICENSE_DTO_REQUEST;

        when(licenseService.getLicensesByWebsite(licenseDtoRequest.getWebsite())).thenReturn(Optional.empty());

        licenseValidator.validate(licenseDtoRequest, LICENSE_ID, bindingResult);

        verify(bindingResult, never()).rejectValue(eq("website"), anyString(), anyString());
    }

    @Test
    void itShouldNotValidateWebsite() {
        LicenseDtoRequest licenseDtoRequest = LICENSE_DTO_REQUEST;

        when(licenseService.getLicensesByWebsite(licenseDtoRequest.getWebsite())).thenReturn(Optional.of(LICENSE));

        licenseValidator.validate(licenseDtoRequest, LICENSE_ID, bindingResult);

        verify(bindingResult).rejectValue("website", "", "Website must be unique");
    }

    @Test
    void itShouldValidateDescription() {
        LicenseDtoRequest licenseDtoRequest = LICENSE_DTO_REQUEST;

        when(licenseService.getLicensesByDescription(licenseDtoRequest.getDescription())).thenReturn(Optional.empty());

        licenseValidator.validate(licenseDtoRequest, LICENSE_ID, bindingResult);

        verify(bindingResult, never()).rejectValue(eq("description"), anyString(), anyString());
    }

    @Test
    void itShouldNotValidateDescription() {
        LicenseDtoRequest licenseDtoRequest = LICENSE_DTO_REQUEST;

        when(licenseService.getLicensesByDescription(licenseDtoRequest.getDescription())).thenReturn(Optional.of(LICENSE));

        licenseValidator.validate(licenseDtoRequest, LICENSE_ID, bindingResult);

        verify(bindingResult).rejectValue("description", "", "Description must be unique");
    }

    @Test
    void itShouldValidateLogo() {
        LicenseDtoRequest request = LICENSE_DTO_REQUEST;

        when(licenseService.getLicensesByLogo(ArrayUtils.toPrimitive(request.getLogo()))).thenReturn(Optional.empty());

        licenseValidator.validate(request, LICENSE_ID, bindingResult);

        verify(bindingResult, never()).rejectValue(eq("logo"), anyString(), anyString());
    }

    @Test
    void itShouldNotValidateLogo() {
        LicenseDtoRequest request = LICENSE_DTO_REQUEST;

        License existingLicense = LICENSE;
        existingLicense.setId(LICENSE_ID2);

        when(licenseService.getLicensesByLogo(ArrayUtils.toPrimitive(request.getLogo()))).thenReturn(Optional.of(existingLicense));

        licenseValidator.validate(request, LICENSE_ID, bindingResult);

        verify(bindingResult).rejectValue("logo", "", "Logo must be unique");
    }

    @Test
    void itShouldNotValidateCredentials() {
        Credential mockCredential =  CREDENTIAL;
        mockCredential.getLicense().setId(2);
        when(licenseService.getCredentialByUsername(CREDENTIAL_DTO.getUsername())).thenReturn(Optional.of(mockCredential));
        when(licenseService.getCredentialByPassword(CREDENTIAL_DTO.getPassword())).thenReturn(Optional.of(mockCredential));

        licenseValidator.validate(LICENSE_DTO_REQUEST, LICENSE_ID, bindingResult);

        verify(bindingResult).rejectValue(eq("credentials[0].username"), anyString(), anyString());
        verify(bindingResult).rejectValue(eq("credentials[0].password"), anyString(), anyString());
    }

    @Test
    void itShouldValidateCredentials() {
        Credential mockCredential =  CREDENTIAL;
        mockCredential.getLicense().setId(LICENSE_ID);
        when(licenseService.getCredentialByUsername(CREDENTIAL_DTO.getUsername())).thenReturn(Optional.of(mockCredential));
        when(licenseService.getCredentialByPassword(CREDENTIAL_DTO.getPassword())).thenReturn(Optional.of(mockCredential));

        licenseValidator.validate(LICENSE_DTO_REQUEST, LICENSE_ID, bindingResult);

        verify(bindingResult, never()).rejectValue(eq("credentials[0].username"), anyString(), anyString());
        verify(bindingResult, never()).rejectValue(eq("credentials[0].password"), anyString(), anyString());
    }
}

