package endava.internship.traininglicensessharing.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
@Builder
@Schema(name = "requestLicenseResponse")
public class RequestDtoResponse {

    @Schema(example = "1",
            description = "Request id")
    private Integer requestId;

    @Schema(example = "PENDING",
            description = "Request status")
    private String status;

    @Schema(example = "19-Jul-2023 00:00",
            description = "Date when request was created")
    private String requestedOn;

    @Schema(example = "Udemy",
            description = "License name for which the request was made")
    private String app;

    @Schema(example = "20-Jul-2023",
            description = "Date when request was created")
    private String startOfUse;

    @Schema(example = "John Churchill",
            description = "User name which made the request")
    private String user;

    @Schema(example = "Testing",
            description = "User's discipline")
    private String discipline;
}
