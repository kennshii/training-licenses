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
public class CucumberUserDto {

    private String firstName;

    private String lastName;

    private String position;

    private String discipline;

    private String deliveryUnit;

    private LocalDate registrationDate;

    private String isActive;

    private LocalDate lastActive;

    private String email;

}
