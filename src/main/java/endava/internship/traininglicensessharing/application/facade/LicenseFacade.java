package endava.internship.traininglicensessharing.application.facade;

import java.util.List;

import org.springframework.validation.BindingResult;

import endava.internship.traininglicensessharing.application.dto.LicenseDtoRequest;
import endava.internship.traininglicensessharing.application.dto.LicenseDtoResponse;
import jakarta.validation.Valid;

public interface LicenseFacade {

    LicenseDtoResponse addLicense(@Valid LicenseDtoRequest licenseDtoRequest, BindingResult bindingResult);

    List<LicenseDtoResponse> getAllLicences();

    LicenseDtoResponse getLicenseById(Integer id);

    LicenseDtoResponse updateLicense(@Valid LicenseDtoRequest licenseDtoRequest, Integer licenseId, BindingResult bindingResult);

    void deleteLicenseById(String id);
}
