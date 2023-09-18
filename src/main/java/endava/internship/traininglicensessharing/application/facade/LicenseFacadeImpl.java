package endava.internship.traininglicensessharing.application.facade;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import endava.internship.traininglicensessharing.application.dto.LicenseDtoRequest;
import endava.internship.traininglicensessharing.application.dto.LicenseDtoResponse;
import endava.internship.traininglicensessharing.application.exception.ResourceNotFoundException;
import endava.internship.traininglicensessharing.application.exception.ValidationCustomException;
import endava.internship.traininglicensessharing.application.mapper.LicenseMapper;
import endava.internship.traininglicensessharing.application.validator.LicenseValidator;
import endava.internship.traininglicensessharing.domain.entity.License;
import endava.internship.traininglicensessharing.domain.service.LicenseService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LicenseFacadeImpl implements LicenseFacade {

    private final LicenseService licenseService;
    private final LicenseMapper licenseMapper;
    private final LicenseValidator validator;

    @Override
    public List<LicenseDtoResponse> getAllLicences() {
        List<License> licenses = licenseService.getAllLicenses();

        return licenses.stream()
                .map(licenseMapper::licenseToLicenseDtoResponse)
                .sorted(Comparator.comparingInt(LicenseDtoResponse::getId).reversed())
                .toList();
    }

    @Override
    public void deleteLicenseById(String id) {
        licenseService.deleteById(Integer.parseInt(id));
    }

    @Override
    public LicenseDtoResponse getLicenseById(Integer id) {
        Optional<License> optionalLicense = licenseService.getLicenseById(id);

        License license = optionalLicense
                .orElseThrow(() -> new ResourceNotFoundException("License with id = [%d] not found.".formatted(id)));

        return licenseMapper.licenseToLicenseDtoResponse(license);
    }

    @Override
    public LicenseDtoResponse addLicense(LicenseDtoRequest licenseDtoRequest, BindingResult bindingResult) {
        validator.validate(licenseDtoRequest, null, bindingResult);
        validateLicenseDtoRequest(bindingResult);

        License license = licenseMapper.licenseDtoRequestToLicense(licenseDtoRequest);
        License response = licenseService.addLicense(license);
        return licenseMapper.licenseToLicenseDtoResponse(response);
    }

    @Override
    public LicenseDtoResponse updateLicense(LicenseDtoRequest licenseDtoRequest, Integer licenseId, BindingResult bindingResult) {
        validator.validate(licenseDtoRequest, licenseId, bindingResult);
        validateLicenseDtoRequest(bindingResult);

        License oldLicense = licenseService.getLicenseById(licenseId)
                .orElseThrow(() -> new ResourceNotFoundException("License with id = [%d] not found.".formatted(licenseId)));
        License newLicense = licenseMapper.licenseDtoRequestToLicense(licenseDtoRequest);

        License response = licenseService.updateLicense(newLicense, oldLicense);
        return licenseMapper.licenseToLicenseDtoResponse(response);
    }

    private void validateLicenseDtoRequest(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationCustomException(bindingResult);
        }
    }
}
