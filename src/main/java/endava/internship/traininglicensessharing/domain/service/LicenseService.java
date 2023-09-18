package endava.internship.traininglicensessharing.domain.service;

import java.util.List;
import java.util.Optional;

import endava.internship.traininglicensessharing.domain.entity.Credential;
import endava.internship.traininglicensessharing.domain.entity.License;

public interface LicenseService {

    List<License> getAllLicenses();

    Optional<License> getLicenseByName(String name);

    License addLicense(License license);

    Optional<License> getLicenseById(Integer id);

    License updateLicense(License newLicense, License oldLicense);

    License updateCredentials(License newLicense, License licenseToUpdate);

    void deleteById(int id);

    Optional<License> getLicensesByWebsite(String website);

    Optional<License> getLicensesByDescription(String description);

    Optional<License> getLicensesByLogo(byte[] logo);

    Optional<Credential> getCredentialByUsername(String username);

    Optional<Credential> getCredentialByPassword(String password);
}