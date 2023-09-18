package endava.internship.traininglicensessharing.application.mapper;

import static endava.internship.traininglicensessharing.domain.enums.RequestStatus.APPROVED;

import java.time.LocalDate;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import endava.internship.traininglicensessharing.domain.entity.Credential;
import endava.internship.traininglicensessharing.domain.entity.License;
import endava.internship.traininglicensessharing.application.dto.CredentialDto;
import endava.internship.traininglicensessharing.application.dto.LicenseDtoRequest;
import endava.internship.traininglicensessharing.application.dto.LicenseDtoResponse;

@Mapper
public interface LicenseMapper {

    @Mapping(target = "seatsTotal", source = "seats")
    @Mapping(target = "seatsAvailable", expression = "java(getSeatsAvailable(license))")
    @Mapping(target = "isActive", expression = "java(isActive(license))")
    LicenseDtoResponse licenseToLicenseDtoResponse(License license);

    @Mapping(target = "seats", source = "seatsTotal")
    @Mapping(target = "id", ignore = true)
    License licenseDtoRequestToLicense(LicenseDtoRequest licenseDtoRequest);

    @Mapping(target = "id", ignore = true)
    Credential credentialDtoRequestToCredential(CredentialDto dto);

    default Integer getSeatsAvailable(License license) {
        if (null == license.getRequests()) {
            return license.getSeats();
        }

        final Integer numberOfApprovedRequests = Math.toIntExact(license.getRequests().stream()
                .filter(request -> request.getStatus().equals(APPROVED))
                .count());

        return license.getSeats() - numberOfApprovedRequests;
    }

    default Boolean isActive(License license) {
        return license.getExpiresOn() != null && license.getExpiresOn().isAfter(LocalDate.now());
    }
}
