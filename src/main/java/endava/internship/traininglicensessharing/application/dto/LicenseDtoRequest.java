package endava.internship.traininglicensessharing.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import endava.internship.traininglicensessharing.application.dto.deserializer.CustomEnumDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode
@ToString
@Schema(name = "licenseRequest")
public class LicenseDtoRequest {

    @Schema(example = "Coursera",
            description = "Name must be unique",
            minLength = 3, maxLength = 20)
    @NotNull(message = "Name is mandatory")
    @Pattern(regexp = "^[!-/:-@\\[-`{-~\\w\\s]{3,20}$",
            message = "Must be 3 to 20 alphanumeric characters, including special symbols.")
    private String name;

    @Schema(example = "coursera.com",
            description = "Website must be unique",
            minLength = 5, maxLength = 30)
    @NotNull(message = "Website is mandatory")
    @Pattern(regexp = "^[!-/:-@\\[-`{-~\\w\\s]{5,30}$",
            message = "Must be 5 to 30 alphanumeric characters, including special symbols.")
    private String website;

    @Schema(example = "TRAINING",
            description = "Type of the license",
            minLength = 5, maxLength = 20,
            allowableValues = {"TRAINING", "SOFTWARE"})
    @NotNull
    @JsonDeserialize(using = CustomEnumDeserializer.class)
    private String licenseType;

    @Schema(example = "Java certification license",
            description = "Description must be unique",
            minLength = 5, maxLength = 250)
    @Pattern(regexp = "^[,.'\"\\-\\w\\s]{5,250}$",
            message = "Must be 5 to 250 alphanumeric characters, including special symbols.")
    private String description;

    @Schema(example = "null",
            description = "Logo image as byte array, must be unique")
    @Size(min = 2_097_152, max = 10_048_576, message = "Must be between 2MB and 10MB.")
    private Byte[] logo;

    @Schema(example = "99",
            description = "License total cost")
    @NotNull(message = "Cost is mandatory")
    @Max(value = 99999, message = "License cost must not exceed 99999")
    @Min(value = 0, message = "License cost must not be less than 0")
    private BigDecimal cost;

    @Schema(example = "USD")
    @NotNull
    @JsonDeserialize(using = CustomEnumDeserializer.class)
    private String currency;

    @Schema(example = "10",
            description = "Total period of time for which a license was bought")
    @Min(value = 1, message = "License duration must not be less than 1 month")
    @Max(value = 12, message = "License duration must not be more than 1 year")
    @NotNull(message = "Duration period is mandatory")
    private Integer licenseDuration;

    @Schema(example = "17-02-2024",
            description = "Date when the license will expire. Cannot specify the expiration date in the past",
            type = "string",
            pattern = "dd-MM-yyyy")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Future(message = "You cannot specify a license expiration date in the past")
    private LocalDate expiresOn;

    @Schema(example = "false",
            description = "License is repurchased or not, especially at regular intervals")
    @NotNull
    private Boolean isRecurring;

    @Schema(example = "210",
            description = "Total number of seats that are planned for this license")
    @NotNull(message = "Number of seats is mandatory")
    @Max(value = 250, message = "Number of seat must not exceed 250")
    @Min(value = 20, message = "Number of seat must not be less than 20")
    private Integer seatsTotal;

    @Valid
    @NotEmpty
    @Size(max = 10, message = "Must be between 1 and 10 credentials.")
    private List<CredentialDto> credentials;
}