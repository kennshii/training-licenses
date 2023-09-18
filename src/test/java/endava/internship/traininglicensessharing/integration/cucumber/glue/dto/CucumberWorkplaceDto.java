package endava.internship.traininglicensessharing.integration.cucumber.glue.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CucumberWorkplaceDto {

    private String position;

    private String discipline;

    private String deliveryUnit;

}
