package endava.internship.traininglicensessharing.integration.cucumber.glue.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import endava.internship.traininglicensessharing.domain.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CucumberRequestDto {

    private String license;

    private String lastName;

    private String firstName;

    private RequestStatus status;

    private LocalDateTime requestedOn;

    private LocalDate startOfUse;

}
