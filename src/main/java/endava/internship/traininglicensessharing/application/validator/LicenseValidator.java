package endava.internship.traininglicensessharing.application.validator;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import endava.internship.traininglicensessharing.application.dto.CredentialDto;
import endava.internship.traininglicensessharing.application.dto.LicenseDtoRequest;
import endava.internship.traininglicensessharing.domain.entity.Credential;
import endava.internship.traininglicensessharing.domain.entity.License;
import endava.internship.traininglicensessharing.domain.service.LicenseService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LicenseValidator {

    private final LicenseService licenseService;

    public void validate(LicenseDtoRequest request, Integer licenseId, Errors errors) {

        Optional<License> optionalLicenseByName = licenseService.getLicenseByName(request.getName());
        if (optionalLicenseByName.isPresent() && !Objects.equals(optionalLicenseByName.get().getId(), licenseId)) {
            errors.rejectValue("name", "", "Name must be unique");
        }

        Optional<License> optionalLicenseByWebsite = licenseService.getLicensesByWebsite(request.getWebsite());
        if (optionalLicenseByWebsite.isPresent() && !Objects.equals(optionalLicenseByWebsite.get().getId(), licenseId)) {
            errors.rejectValue("website", "", "Website must be unique");
        }

        if (null != request.getDescription()) {
            Optional<License> optionalLicenseByDescription = licenseService.getLicensesByDescription(request.getDescription());
            if (optionalLicenseByDescription.isPresent() && !Objects.equals(optionalLicenseByDescription.get().getId(), licenseId)) {
                errors.rejectValue("description", "", "Description must be unique");
            }
        }

        if (null != request.getLogo()) {
            Optional<License> optionalLicenseByLogo = licenseService.getLicensesByLogo(ArrayUtils.toPrimitive(request.getLogo()));
            if (optionalLicenseByLogo.isPresent() && !Objects.equals(optionalLicenseByLogo.get().getId(), licenseId)) {
                errors.rejectValue("logo", "", "Logo must be unique");
            }
        }

        final Set<CredentialDto> credentialSet = new HashSet<>();

        if (null != request.getCredentials()) {
            for (CredentialDto credentialDto : request.getCredentials()) {
                Optional<Credential> optionalCredentialByUsername = licenseService.getCredentialByUsername(credentialDto.getUsername());
                Optional<Credential> optionalCredentialByPassword = licenseService.getCredentialByPassword(credentialDto.getPassword());

                Integer licenseIdByUsername = null;
                if(optionalCredentialByUsername.isPresent()){
                    licenseIdByUsername = optionalCredentialByUsername.get().getLicense().getId();
                }

                Integer licenseIdByPassword = null;
                if(optionalCredentialByPassword.isPresent()){
                    licenseIdByPassword = optionalCredentialByPassword.get().getLicense().getId();
                }

                boolean matchUsername = credentialSet.stream()
                        .anyMatch(credentialDto1 -> credentialDto1.getUsername().equals(credentialDto.getUsername()));
                boolean matchPassword = credentialSet.stream()
                        .anyMatch(credentialDto1 -> credentialDto1.getPassword().equals(credentialDto.getPassword()));

                String usernameField = "credentials[" + credentialSet.size() + "].username";
                String passwordField = "credentials[" + credentialSet.size() + "].password";

                if ((licenseIdByUsername != null && !Objects.equals(licenseIdByUsername, licenseId)) || matchUsername) {
                    errors.rejectValue(usernameField, "", "Username must be unique");
                }
                if ((licenseIdByPassword != null && !Objects.equals(licenseIdByPassword, licenseId)) || matchPassword) {
                    errors.rejectValue(passwordField, "", "Password must be unique");
                }
                credentialSet.add(credentialDto);
            }
        }
    }
}
