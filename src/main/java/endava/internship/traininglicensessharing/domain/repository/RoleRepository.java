package endava.internship.traininglicensessharing.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import endava.internship.traininglicensessharing.domain.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
