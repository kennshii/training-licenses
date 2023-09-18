package endava.internship.traininglicensessharing.domain.entity;

import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarbinaryJdbcType;
import org.springframework.format.annotation.DateTimeFormat;

import endava.internship.traininglicensessharing.domain.enums.Currency;
import endava.internship.traininglicensessharing.domain.enums.LicenseType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "license")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "id")
@ToString
public class License {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(nullable = false,
            unique = true)
    private String name;

    @Column(nullable = false,
            unique = true)
    private String website;

    @Enumerated(STRING)
    @Column(name = "license_type",
            nullable = false)
    private LicenseType licenseType;

    @Column(unique = true)
    private String description;

    @JdbcType(VarbinaryJdbcType.class)
    @Column(unique = true)
    private byte[] logo;

    @Column(nullable = false)
    private BigDecimal cost;

    @Enumerated(STRING)
    @Column(nullable = false)
    private Currency currency;

    @Column(name = "duration",
            nullable = false)
    private Integer licenseDuration;

    @Column(name = "expires_on",
            nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Temporal(value = TemporalType.DATE)
    private LocalDate expiresOn;

    @Column(name = "is_recurring",
            nullable = false)
    private Boolean isRecurring;

    @Column(nullable = false)
    private Integer seats;

    @OneToMany(cascade = REMOVE,
            mappedBy = "license")
    private List<Credential> credentials = new ArrayList<>();

    @OneToMany(cascade = REMOVE,
            mappedBy = "license")
    private Set<Request> requests = new HashSet<>();
}
