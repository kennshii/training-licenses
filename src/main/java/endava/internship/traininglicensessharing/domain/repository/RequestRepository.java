package endava.internship.traininglicensessharing.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import endava.internship.traininglicensessharing.domain.entity.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    List<Request> getRequestsByUserLastNameAndLicenseName(String userName, String licenseName);
}
