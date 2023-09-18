package endava.internship.traininglicensessharing.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import endava.internship.traininglicensessharing.domain.entity.License;
import jakarta.transaction.Transactional;

@Repository
public interface LicenseRepository extends JpaRepository<License, Integer> {
    @Query(value = """
            SELECT l.*
            FROM license l
            WHERE expires_on > CURRENT_DATE
            ORDER BY expires_on;
            """, nativeQuery = true)
    List<License> getExpiringLicenses();

    @Query(value = """
            SELECT l.*
            FROM license l
            LEFT JOIN request r ON l.id = r.license_id
            WHERE r.license_id IS null
            """, nativeQuery = true)
    List<License> getUnusedLicenses();

    Optional<License> getLicenseByName(String name);

    @Transactional
    void deleteLicenseByName(String name);

    @Query(value = """
            SELECT CAST(COALESCE(SUM(l.cost), 0) AS bigint)
            FROM License l
            WHERE EXTRACT(YEAR FROM (l.expires_on - (CAST(l.duration || ' months' AS INTERVAL)))) = EXTRACT(YEAR FROM CURRENT_DATE);
            """, nativeQuery = true)
    Long getTotalSumOfLicenses();

    Optional<License> getLicensesByWebsite(String website);

    Optional<License> getLicensesByDescription(String description);

    Optional<License> getLicensesByLogo(byte[] logo);

}
