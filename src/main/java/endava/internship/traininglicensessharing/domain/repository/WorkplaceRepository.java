package endava.internship.traininglicensessharing.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import endava.internship.traininglicensessharing.domain.entity.Workplace;

public interface WorkplaceRepository extends JpaRepository<Workplace, Integer> {

    Optional<Workplace> getWorkplaceByPositionIdAndDeliveryUnitIdAndDisciplineId(Integer positionId, Integer deliveryUnitId, Integer disciplineId);

}
