package endava.internship.traininglicensessharing.application.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Schema(name = "requestLicenseRequest")
public class RequestDtoRequest {

    @Schema(example = "[1, 2, 3]",
            description = "Requests ids")
    @NotEmpty
    List<Integer> requestIds;

    @Schema(example = "APPROVED",
            description = "Requests status to update to")
    @NotEmpty
    String status;
}
