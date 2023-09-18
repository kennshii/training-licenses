package endava.internship.traininglicensessharing.integration.cucumber.glue.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import endava.internship.traininglicensessharing.application.mapper.LicenseMapper;
import endava.internship.traininglicensessharing.application.dto.LicenseDtoRequest;
import endava.internship.traininglicensessharing.application.dto.LicenseDtoResponse;

@Component
public class CucumberLicenseMapper {

    @Autowired
    private LicenseMapper licenseMapper;

    public LicenseDtoResponse licenseDtoRequestToLicenseDtoResponse(LicenseDtoRequest licenseDtoRequest) {
        return LicenseDtoResponse.builder()
                .name(licenseDtoRequest.getName())
                .website(licenseDtoRequest.getWebsite())
                .logo(licenseDtoRequest.getLogo())
                .expiresOn(licenseDtoRequest.getExpiresOn())
                .currency(licenseDtoRequest.getCurrency())
                .licenseType(licenseDtoRequest.getLicenseType())
                .licenseDuration(licenseDtoRequest.getLicenseDuration())
                .seatsTotal(licenseDtoRequest.getSeatsTotal())
                .isRecurring(licenseDtoRequest.getIsRecurring())
                .credentials(licenseDtoRequest.getCredentials())
                .cost(licenseDtoRequest.getCost())
                .description(licenseDtoRequest.getDescription())
                .isActive(licenseMapper.isActive(licenseMapper.licenseDtoRequestToLicense(licenseDtoRequest)))
                .seatsAvailable(licenseMapper.getSeatsAvailable(licenseMapper.licenseDtoRequestToLicense(licenseDtoRequest)))
                .build();
    }
}
