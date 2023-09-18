package endava.internship.traininglicensessharing.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import endava.internship.traininglicensessharing.domain.entity.Position;
import jakarta.transaction.Transactional;

public interface PositionRepository extends JpaRepository<Position, Integer> {
    Optional<Position> getPositionByName(String name);

    @Transactional
    void deletePositionByName(String name);
}
