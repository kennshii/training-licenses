package endava.internship.traininglicensessharing.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import endava.internship.traininglicensessharing.domain.entity.LicenseCost;


public interface LicenseCostRepository extends Repository<LicenseCost, Integer> {
    @Query("FROM LicenseCost WHERE YEAR(startDate) = YEAR(current_date)")
    List<LicenseCost> getCostsForCurrentYear();

    @Query("FROM LicenseCost WHERE YEAR(startDate) = YEAR(current_date)-1")
    List<LicenseCost> getCostsForPreviousYear();

    @Query("FROM LicenseCost " +
            "WHERE (YEAR(startDate) = YEAR(current_date) AND MONTH(startDate) <= MONTH(current_date))" +
            "OR" +
            "(YEAR(startDate) = YEAR(current_date)-1 AND MONTH(startDate) > MONTH(current_date))")
    List<LicenseCost> getCostsForLast12Months();

}
