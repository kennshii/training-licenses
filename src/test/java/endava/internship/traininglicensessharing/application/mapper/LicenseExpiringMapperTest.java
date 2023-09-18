package endava.internship.traininglicensessharing.application.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import endava.internship.traininglicensessharing.domain.entity.License;
import endava.internship.traininglicensessharing.application.dto.LicenseExpiringDto;

class LicenseExpiringMapperTest {

    LicenseExpiringMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = LicenseExpiringMapper.INSTANCE;
    }

    @Test
    void toLicenseExpiringDto() {
        License license = License.builder()
                .id(1)
                .name("Udemy")
                .cost(BigDecimal.valueOf(5000))
                .licenseDuration(5)
                .expiresOn(LocalDate.now())
                .build();

        LicenseExpiringDto licenseExpiringDto = mapper.toLicenseExpiringDto(license);

        assertAll(
                () -> assertEquals(license.getId(), licenseExpiringDto.getId()),
                () -> assertEquals(license.getName(), licenseExpiringDto.getName()),
                () -> assertEquals(license.getCost().toString(), licenseExpiringDto.getCost()),
                () -> assertEquals(license.getLicenseDuration(), licenseExpiringDto.getLicenseDuration()),
                () -> assertEquals(license.getExpiresOn().toString(), licenseExpiringDto.getExpiresOn())
        );
    }
}