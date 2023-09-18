package endava.internship.traininglicensessharing.domain.entity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import endava.internship.traininglicensessharing.domain.enums.RequestStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "request")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Request {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "license_id",
            referencedColumnName = "id",
            nullable = false)
    private License license;

    @ManyToOne
    @JoinColumn(name = "user_id",
            referencedColumnName = "id",
            nullable = false)
    private User user;

    @Enumerated(STRING)
    @Column(nullable = false)
    private RequestStatus status;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "requested_on",
            nullable = false)
    private LocalDateTime requestedOn;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "start_of_use")
    private LocalDate startOfUse;
}
