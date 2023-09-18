package endava.internship.traininglicensessharing.utils;

import static endava.internship.traininglicensessharing.domain.enums.Currency.USD;
import static endava.internship.traininglicensessharing.domain.enums.LicenseType.TRAINING;
import static java.time.Month.DECEMBER;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import endava.internship.traininglicensessharing.domain.entity.Credential;
import endava.internship.traininglicensessharing.domain.entity.License;
import endava.internship.traininglicensessharing.domain.enums.Currency;
import endava.internship.traininglicensessharing.domain.enums.LicenseType;
import endava.internship.traininglicensessharing.application.dto.CredentialDto;
import endava.internship.traininglicensessharing.application.dto.LicenseDtoRequest;
import endava.internship.traininglicensessharing.application.dto.LicenseDtoResponse;
import endava.internship.traininglicensessharing.application.dto.LicenseExpiringDto;
import endava.internship.traininglicensessharing.application.dto.LicenseUnusedDto;

public class LicenseTestUtils {

    public static final License LICENSE1 = License.builder()
            .name("License")
            .website("https://example.com")
            .licenseType(LicenseType.TRAINING)
            .description("License description")
            .cost(BigDecimal.valueOf(9.99))
            .currency(Currency.USD)
            .licenseDuration(6)
            .expiresOn(LocalDate.of(2023, 8, 31))
            .isRecurring(false)
            .seats(10)
            .build();

    public static final License LICENSE2 = License.builder()
            .name("License 2")
            .website("https://example2.com")
            .licenseType(LicenseType.SOFTWARE)
            .description("License 2 description")
            .cost(BigDecimal.valueOf(19.99))
            .currency(Currency.USD)
            .licenseDuration(1)
            .expiresOn(LocalDate.of(2023, 12, 31))
            .isRecurring(true)
            .seats(5)
            .build();

    public static final License LICENSE3 = License.builder()
            .name("License 2")
            .website("https://example2.com")
            .licenseType(LicenseType.SOFTWARE)
            .description("License 2 description")
            .cost(BigDecimal.valueOf(19.99))
            .currency(Currency.USD)
            .licenseDuration(1)
            .expiresOn(LocalDate.of(2023, 12, 31))
            .isRecurring(true)
            .seats(5)
            .build();

    public static final LicenseExpiringDto LICENSE_EXPIRING_DTO1 = LicenseExpiringDto.builder()
            .name(LICENSE1.getName())
            .cost(LICENSE1.getCost().toString())
            .licenseDuration(LICENSE1.getLicenseDuration())
            .expiresOn(LICENSE1.getExpiresOn().toString())
            .build();

    public static final LicenseUnusedDto LICENSE_UNUSED_DTO2 = LicenseUnusedDto.builder()
            .name(LICENSE2.getName())
            .cost(LICENSE2.getCost().toString())
            .period(-1)
            .build();

    public static final LicenseUnusedDto LICENSE_UNUSED_DTO3 = LicenseUnusedDto.builder()
            .name(LICENSE3.getName())
            .cost(LICENSE3.getCost().toString())
            .period(-1)
            .build();

    public static final byte[] LOGO = {69, 121, 101, 45, 62, 118, 101, 114, 61, 101, 98};

    public static final Byte[] LICENSE_LOGO = {69, 121, 101, 45, 62, 118, 101, 114, 61, 101, 98};

    public static final Byte[] REQUEST_LOGO = {69, 121, 101, 45, 62, 118, 101, 114, 61, 101, 98};

    public static final String DESCRIPTION = "test description";

    public static final String LICENSE_NAME = "TEST";

    public static final String LICENSE_WEBSITE = "www.TEST.com";

    public static final BigDecimal COST = BigDecimal.valueOf(499.00);

    public static final LocalDate EXPIRES_ON = LocalDate.of(2023, DECEMBER, 13);

    public static final int SEATS = 150;

    public static final int LICENSE_DURATION = 1;

    public static final String LICENSE_TYPE = "TRAINING";

    public static final String CURRENCY = "USD";

    public static final String CREDENTIAL_USERNAME = "john.smith@endava.com";

    public static final String CREDENTIAL_PASSWORD = "johnsmith";

    private static final License license = License.builder()
            .name(LICENSE_NAME)
            .website(LICENSE_WEBSITE)
            .licenseType(TRAINING)
            .description(DESCRIPTION)
            .cost(COST)
            .currency(USD)
            .logo(LOGO)
            .expiresOn(EXPIRES_ON)
            .isRecurring(false)
            .seats(SEATS)
            .licenseDuration(LICENSE_DURATION)
            .build();

    public static final Credential CREDENTIAL = Credential.builder()
            .username(CREDENTIAL_USERNAME)
            .password(CREDENTIAL_PASSWORD)
            .license(license)
            .build();

    public final static CredentialDto CREDENTIAL_DTO = CredentialDto.builder()
            .username(CREDENTIAL_USERNAME)
            .password(CREDENTIAL_PASSWORD)
            .build();

    public final static LicenseDtoResponse LICENSE_DTO_RESPONSE = LicenseDtoResponse.builder()
            .name(LICENSE_NAME)
            .website(LICENSE_WEBSITE)
            .licenseType(LICENSE_TYPE)
            .description(DESCRIPTION)
            .logo(LICENSE_LOGO)
            .cost(COST)
            .currency(CURRENCY)
            .expiresOn(EXPIRES_ON)
            .isRecurring(false)
            .seatsTotal(SEATS)
            .seatsAvailable(SEATS)
            .isActive(true)
            .credentials(List.of(CREDENTIAL_DTO))
            .licenseDuration(LICENSE_DURATION)
            .build();

    public final static LicenseDtoRequest LICENSE_DTO_REQUEST = LicenseDtoRequest.builder()
            .name(LICENSE_NAME)
            .website(LICENSE_WEBSITE)
            .licenseType(LICENSE_TYPE)
            .description(DESCRIPTION)
            .logo(REQUEST_LOGO)
            .cost(COST)
            .currency(CURRENCY)
            .expiresOn(EXPIRES_ON)
            .isRecurring(false)
            .seatsTotal(SEATS)
            .credentials(List.of(CREDENTIAL_DTO))
            .licenseDuration(LICENSE_DURATION)
            .build();

    public final static License LICENSE = getLicense();

    private static License getLicense() {
        license.setCredentials(List.of(CREDENTIAL));
        return license;
    }

    public static final License LICENSE_NO_CREDENTIALS = License.builder()
            .name(LICENSE_NAME)
            .website(LICENSE_WEBSITE)
            .licenseType(TRAINING)
            .description(DESCRIPTION)
            .cost(COST)
            .currency(USD)
            .logo(LOGO)
            .expiresOn(EXPIRES_ON)
            .isRecurring(false)
            .credentials(Collections.emptyList())
            .seats(SEATS)
            .licenseDuration(LICENSE_DURATION)
            .build();

    public static final Integer LICENSE_ID = 1;

    public static final Integer LICENSE_ID2 = 2;

    public static final String DESCRIPTION2 = "test description2";

    public static final String LICENSE_NAME2 = "TEST2";

    public static final String LICENSE_WEBSITE2 = "www.TEST2.com";

    public static final String NOT_FOUND = "License with id = [%d] not found.".formatted(LICENSE_ID);

    public static final License LICENSE_NO_CREDENTIALS_2 = License.builder()
            .name(LICENSE_NAME2)
            .website(LICENSE_WEBSITE2)
            .licenseType(TRAINING)
            .description(DESCRIPTION2)
            .cost(COST)
            .currency(USD)
            .logo(LOGO)
            .expiresOn(EXPIRES_ON)
            .isRecurring(false)
            .credentials(Collections.emptyList())
            .seats(SEATS)
            .licenseDuration(LICENSE_DURATION)
            .build();

    public static final License LICENSE4 = License.builder()
            .name(LICENSE_NAME)
            .website(LICENSE_WEBSITE)
            .licenseType(TRAINING)
            .description(DESCRIPTION)
            .cost(COST)
            .currency(USD)
            .logo(LOGO)
            .expiresOn(EXPIRES_ON)
            .isRecurring(false)
            .credentials(List.of(CREDENTIAL))
            .seats(SEATS)
            .licenseDuration(LICENSE_DURATION)
            .build();
}
