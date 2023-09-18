package endava.internship.traininglicensessharing.domain.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Exclude
    private Integer userId;

    @Column(name = "first_name",
            nullable = false)
    private String firstName;

    @Column(name = "last_name",
            nullable = false)
    private String lastName;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "workplace_id",
            referencedColumnName = "id")
    private Workplace workplace;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @Column(name = "registration_date",
            nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate registrationDate;

    @Column(name = "is_active",
            nullable = false)
    private Boolean isActive;

    @Column(name = "last_active")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate lastActive;

    @Column(unique = true,nullable = false,updatable = false,insertable = false)
    private String email;
}
