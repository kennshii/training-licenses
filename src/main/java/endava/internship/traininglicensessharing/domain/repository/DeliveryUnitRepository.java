package endava.internship.traininglicensessharing.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import endava.internship.traininglicensessharing.domain.entity.DeliveryUnit;
import jakarta.transaction.Transactional;

public interface DeliveryUnitRepository extends JpaRepository<DeliveryUnit, Integer> {
    Optional<DeliveryUnit> getDeliveryUnitByName(String name);

    @Transactional
    void deleteDeliveryUnitByName(String name);
}
