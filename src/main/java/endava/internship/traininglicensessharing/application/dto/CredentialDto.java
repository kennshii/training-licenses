package endava.internship.traininglicensessharing.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@Schema(name = "credential")
public class CredentialDto {

    @Schema(example = "firstname.lastname@endava.com",
            description = "Username must be unique")
    @NotBlank
    @Pattern(regexp = "^[!-/:-@\\[-`{-~\\w\\s]+@endava\\.com$", message = "Should be an Endava email.")
    private String username;

    @Schema(example = "s69cSYv4k&jBgJw2",
            description = "Password must be unique",
            minLength = 5, maxLength = 20)
    @NotBlank
    @Pattern(regexp = "^[!-/:-@\\[-`{-~\\w]{5,20}$",
            message = "5 to 20 alphanumeric characters, including special symbols.")
    private String password;
}