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
@Builder
@ToString
@EqualsAndHashCode
@Schema(name = "unusedLicense")
public class LicenseUnusedDto {

    @Schema(example = "1",
            description = "License identification number")
    private Integer id;

    @Schema(example = "Oracle",
            description = "Name is unique")
    private String name;

    @Schema(example = "99",
            description = "Total cost for license per year")
    private String cost;

    @Schema(example = "10",
            description = "Total license' unused period of time")
    private Integer period;

    @Schema(example = "Month")
    private String durationUnit;
}