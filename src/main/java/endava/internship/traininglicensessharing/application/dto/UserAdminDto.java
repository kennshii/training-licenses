package endava.internship.traininglicensessharing.application.dto;

import endava.internship.traininglicensessharing.domain.enums.Status;
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
@ToString
@EqualsAndHashCode
@Schema(name = "userAdmin")
@Builder
public class UserAdminDto {

    @Schema(example = "1",
            description = "User identification number")
    private Integer id;

    @Schema(example = "John Smith",
            description = "Firstname and lastname of the user")
    private String name;

    @Schema(example = "Senior Business Analyst",
            description = "Role of the user in Endava")
    private String companyRole;

    @Schema(example = "Development",
            description = "Discipline of the User in Endava")
    private String discipline;

    @Schema(example = "MDD",
            description = "Users' discipline unit")
    private String deliveryUnit;

    @Schema(example = "15",
            description = "Number of days or years when user last accessed the site")
    private Integer lastActiveTime;

    @Schema(example = "days",
            description = "Measurement unit for period when user last accessed the site: days or years")
    private String lastActivePeriod;

    @Schema(example = "ACTIVE",
            description = "Inactive users are those who didn't access Training licenses site in the last 12 months")
    private Status status;

    @Schema(example = "ADMIN",
            description = "Roles that user has in the system. One user can have only 1 Role",
            allowableValues = {"ADMIN", "REVIEWER", "USER"})
    private String role;

    @Schema(example = "testFirstName.testLastName@endava.com",
            description = "Email address of a user")
    private String email;
}