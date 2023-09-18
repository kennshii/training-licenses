package endava.internship.traininglicensessharing.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import endava.internship.traininglicensessharing.domain.entity.Credential;

public interface CredentialRepository extends JpaRepository<Credential, Integer> {
    Optional<Credential> getCredentialByUsername(String username);

    Optional<Credential> getCredentialByPassword(String password);
}
