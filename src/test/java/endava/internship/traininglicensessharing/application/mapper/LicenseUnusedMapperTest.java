package endava.internship.traininglicensessharing.application.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import endava.internship.traininglicensessharing.domain.entity.License;
import endava.internship.traininglicensessharing.application.dto.LicenseUnusedDto;

class LicenseUnusedMapperTest {

    LicenseUnusedMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = LicenseUnusedMapper.INSTANCE;
    }

    @Test
    void toLicenseUnusedDto() {
        License license = License.builder()
                .id(1)
                .name("Udemy")
                .cost(BigDecimal.valueOf(5000))
                .licenseDuration(5)
                .expiresOn(LocalDate.now())
                .build();

        LicenseUnusedDto licenseUnusedDto = mapper.toLicenseUnusedDto(license);

        assertAll(
                () -> assertEquals(license.getId(), licenseUnusedDto.getId()),
                () -> assertEquals(license.getName(), licenseUnusedDto.getName()),
                () -> assertEquals(license.getCost().toString(), licenseUnusedDto.getCost())
        );
    }
}