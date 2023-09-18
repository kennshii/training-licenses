package endava.internship.traininglicensessharing.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.annotation.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import endava.internship.traininglicensessharing.domain.enums.LicenseType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "license_cost_view")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Immutable
public class LicenseCost {
    @Id
    @Column(updatable = false, insertable = false, nullable = false)
    @EqualsAndHashCode.Exclude
    private Integer id;

    @Column(name = "license_type", updatable = false, insertable = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private LicenseType licenseType;

    @Column(name = "license_cost", updatable = false, insertable = false, nullable = false)
    private BigDecimal licenceCost;

    @Column(name = "expires_on", updatable = false, insertable = false, nullable = false)
    @DateTimeFormat
    private LocalDate expireDate;

    @Column(name = "start_date", updatable = false, insertable = false, nullable = false)
    @DateTimeFormat
    private LocalDate startDate;

}
