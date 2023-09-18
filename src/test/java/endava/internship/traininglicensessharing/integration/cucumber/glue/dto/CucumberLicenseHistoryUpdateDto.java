package endava.internship.traininglicensessharing.integration.cucumber.glue.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CucumberLicenseHistoryUpdateDto {

    private LocalDate startDate;

    private String license;

}
