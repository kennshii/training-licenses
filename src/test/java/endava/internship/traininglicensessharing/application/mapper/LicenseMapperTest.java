package endava.internship.traininglicensessharing.application.mapper;

import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE_DTO_REQUEST;
import static endava.internship.traininglicensessharing.utils.LicenseTestUtils.LICENSE_DTO_RESPONSE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import endava.internship.traininglicensessharing.domain.entity.License;
import endava.internship.traininglicensessharing.application.dto.LicenseDtoResponse;


class LicenseMapperTest {

    private final LicenseMapper licenseMapper = Mappers.getMapper(LicenseMapper.class);

    @Test
    void itShouldMapLicenseToLicenseDto() {
        LicenseDtoResponse licenseToLicenseDtoResponse = licenseMapper.licenseToLicenseDtoResponse(LICENSE);

        assertThat(licenseToLicenseDtoResponse).isEqualTo(LICENSE_DTO_RESPONSE);
    }

    @Test
    void itShouldMapLicenseDtoToLicense() {
        License licenseDtoToLicense = licenseMapper.licenseDtoRequestToLicense(LICENSE_DTO_REQUEST);

        assertThat(licenseDtoToLicense).isEqualTo(LICENSE);
    }
}