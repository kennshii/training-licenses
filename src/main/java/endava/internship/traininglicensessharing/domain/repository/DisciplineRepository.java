package endava.internship.traininglicensessharing.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import endava.internship.traininglicensessharing.domain.entity.Discipline;
import jakarta.transaction.Transactional;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Integer> {

    Optional<Discipline> getDisciplineByName(String name);

    @Transactional
    void deleteDisciplineByName(String name);

}
