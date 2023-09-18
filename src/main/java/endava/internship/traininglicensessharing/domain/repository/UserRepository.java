package endava.internship.traininglicensessharing.domain.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import endava.internship.traininglicensessharing.domain.entity.User;
import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> getUserByLastName(String lastName);

    @Transactional
    void deleteUserByFirstNameAndLastName(String firstName, String lastName);

    @Query(value = """
            SELECT COUNT(*) as numUsers
            FROM User u
            """)
    long getTotalCountOfUsers();

    @Query("SELECT COUNT(*) as delta " +
            "FROM User u " +
            "WHERE EXTRACT(MONTH FROM u.registrationDate) = EXTRACT(MONTH FROM CURRENT_DATE)")
    long getCountOfNewUsers();

    @Query("SELECT d.id as id, d.name as discipline, COUNT(u.userId) as userNum " +
            "FROM Discipline d " +
            "LEFT JOIN Workplace w ON d.id = w.discipline.id " +
            "LEFT JOIN User u ON u.workplace.id = w.id " +
            "GROUP BY d.name, d.id " +
            "ORDER BY COUNT(u.userId) DESC, discipline DESC ")
    List<Map<String, Object>> getUsersPerDepartment();

}
