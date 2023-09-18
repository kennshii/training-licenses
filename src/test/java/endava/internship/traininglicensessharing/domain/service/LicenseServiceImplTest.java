package endava.internship.traininglicensessharing.domain.service;


import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.CREDENTIAL;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE4;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE_ID;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE_NAME;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE_NO_CREDENTIALS;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE_NO_CREDENTIALS_2;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import endava.internship.traininglicensessharing.domain.entity.Credential;
import endava.internship.traininglicensessharing.domain.entity.License;
import endava.internship.traininglicensessharing.domain.repository.CredentialRepository;
import endava.internship.traininglicensessharing.domain.repository.LicenseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@Slf4j
class LicenseServiceImplTest {

    @Mock
    private LicenseRepository licenseRepository;

    @Mock
    private CredentialRepository credentialRepository;

    @InjectMocks
    private LicenseServiceImpl licenseService;

    @Test
    void itShouldGetAllLicenses() {
        final List<License> licenseList = List.of(LICENSE);

        when(licenseRepository.findAll()).thenReturn(licenseList);

        List<License> licenses = licenseService.getAllLicenses();

        assertThat(licenses.size()).isEqualTo(1);
        assertTrue(licenses.contains(LICENSE));
        assertThat(licenses.get(0).getCredentials().contains(CREDENTIAL)).isTrue();
    }

    @Test
    void itShouldFindLicenseByName() {
        when(licenseRepository.getLicenseByName(LICENSE_NAME)).thenReturn(Optional.of(LICENSE));

        Optional<License> actualResult = licenseService.getLicenseByName(LICENSE_NAME);

        assertThat(actualResult).contains(LICENSE);
    }

    @Test
    void itShouldNotFindLicenseByName() {
        when(licenseRepository.getLicenseByName(LICENSE_NAME)).thenReturn(Optional.empty());

        Optional<License> actualResult = licenseService.getLicenseByName(LICENSE_NAME);

        assertThat(actualResult).isEmpty();
    }

    @Test
    void itShouldAddLicense() {
        List<Credential> credentials = List.of(CREDENTIAL);
        when(licenseRepository.save(LICENSE)).thenReturn(LICENSE);

        License actualResult = licenseService.addLicense(LICENSE);

        assertThat(actualResult).isEqualTo(LICENSE);
        verify(licenseRepository).save(LICENSE);
        verify(credentialRepository).saveAll(argThat(credentials::equals));
    }

    @Test
    void itShouldUpdateCredentials() {
        License actualResult = licenseService.updateCredentials(LICENSE_NO_CREDENTIALS, LICENSE4);

        assertAll( () -> {
                assertThat(actualResult).isEqualTo(LICENSE_NO_CREDENTIALS);
                assertTrue(actualResult.getCredentials().isEmpty());
            }
        );
    }

    @Test
    void itShouldUpdateLicense() {
        when(licenseRepository.save(LICENSE4)).thenReturn(LICENSE4);
        License actualResult = licenseService.updateLicense(LICENSE_NO_CREDENTIALS_2, LICENSE4);


        assertAll( () -> {
                assertThat(actualResult).isEqualTo(LICENSE_NO_CREDENTIALS_2);
                assertTrue(actualResult.getCredentials().isEmpty());
            }
        );
    }

    @Test
    void itShouldGetLicenseById() {
        LICENSE.setId(LICENSE_ID);
        when(licenseRepository.findById(LICENSE_ID)).thenReturn(Optional.of(LICENSE));

        Optional<License> actualResult = licenseService.getLicenseById(LICENSE_ID);

        assertThat(actualResult).contains(LICENSE);
    }

    @Test
    void itShouldReturnEmpty_WhenGetLicenseByUnknownId() {
        when(licenseRepository.findById(LICENSE_ID)).thenReturn(Optional.empty());

        Optional<License> actualResult = licenseService.getLicenseById(LICENSE_ID);

        assertThat(actualResult).isEmpty();
    }

    @Test
    void itShouldDeleteLicenseById() {
        int idToDelete = 1;
        when(licenseRepository.findById(idToDelete)).thenReturn(Optional.ofNullable(LICENSE));

        licenseService.deleteById(idToDelete);

        assert LICENSE != null;
        verify(licenseRepository).delete(LICENSE);
    }

    @Test
    void deleteById_InvalidId_EntityNotFoundExceptionThrown() {
        int idToDelete = 123;

        when(licenseRepository.findById(idToDelete)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> licenseService.deleteById(idToDelete));
    }
}