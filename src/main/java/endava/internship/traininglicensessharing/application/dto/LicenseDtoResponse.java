package endava.internship.traininglicensessharing.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Builder
@Setter
@Getter
@ToString
@EqualsAndHashCode(exclude = "id")
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "licenseResponse")
public class LicenseDtoResponse {

    @Schema(example = "1",
            description = "License' identification number. Id is unique")
    private Integer id;

    @Schema(example = "Udemy",
            description = "Name is unique")
    private String name;

    @Schema(example = "udemy.com",
            description = "Website is unique")
    private String website;

    @Schema(example = "TRAINING",
            description = "Type of the license",
            allowableValues = {"TRAINING", "SOFTWARE"})
    private String licenseType;

    @Schema(example = "Certification license for Java")
    private String description;

    @Schema(example = "[1,2,3]",
            description = "Logo image as byte array, must be unique")
    private Byte[] logo;

    @Schema(example = "99",
            description = "License total cost")
    private BigDecimal cost;

    @Schema(example = "USD")
    private String currency;

    @Schema(example = "10",
            description = "Total period of time for which a license was bought")
    private Integer licenseDuration;

    @Schema(example = "17-02-2024",
            description = "Date when the license will expire",
            type = "string",
            pattern = "dd-MM-yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate expiresOn;

    @Schema(example = "false",
            description = "License is repurchased or not, especially at regular intervals")
    private Boolean isRecurring;

    @Schema(example = "230",
            description = "Total number of seats that are planned for this license")
    private Integer seatsTotal;

    @Schema(example = "200",
            description = "Users' capacity per license")
    private Integer seatsAvailable;

    @Schema(description = "List of license' credentials")
    private List<CredentialDto> credentials;

    @Schema(example = "true",
            description = "Active license did not passed its available time and was repurchased/extended")
    private Boolean isActive;
}