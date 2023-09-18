package endava.internship.traininglicensessharing.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Schema(name = "expiringLicense")
public class LicenseExpiringDto {

    @Schema(example = "1",
            description = "License identification number")
    private Integer id;

    @Schema(example = "Oracle",
            description = "Name is unique")
    private String name;

    @Schema(example = "99",
            description = "Total cost for license per year")
    private String cost;

    @Schema(example = "11",
            description = "Total period of time for which a license was bought. Between 1 and 12")
    private Integer licenseDuration;

    @Schema(example = "Month")
    private String durationUnit;

    @Schema(example = "17-02-2024",
            description = "Date when the license will expire. Cannot specify the expiration date in the past",
            type = "string",
            pattern = "dd-MM-yyyy")
    private String expiresOn;
}
