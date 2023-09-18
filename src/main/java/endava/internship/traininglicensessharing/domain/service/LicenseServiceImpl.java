package endava.internship.traininglicensessharing.domain.service;

import static endava.internship.traininglicensessharing.domain.cache.CacheContext.AVERAGE_COSTS_OVERVIEW_CACHE;
import static endava.internship.traininglicensessharing.domain.cache.CacheContext.COST_DATA_OVERVIEW_CACHE;
import static endava.internship.traininglicensessharing.domain.cache.CacheContext.LICENSES_CACHE;
import static endava.internship.traininglicensessharing.domain.cache.CacheContext.LICENSE_CACHE;
import static endava.internship.traininglicensessharing.domain.cache.CacheContext.REQUESTS_CACHE;
import static endava.internship.traininglicensessharing.domain.cache.CacheContext.REQUEST_CACHE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import endava.internship.traininglicensessharing.domain.entity.Credential;
import endava.internship.traininglicensessharing.domain.entity.License;
import endava.internship.traininglicensessharing.domain.repository.CredentialRepository;
import endava.internship.traininglicensessharing.domain.repository.LicenseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LicenseServiceImpl implements LicenseService {

    private final LicenseRepository licenseRepository;
    private final CredentialRepository credentialRepository;

    @Cacheable(value = LICENSES_CACHE, keyGenerator = "cacheKeyGenerator")
    @Override
    public List<License> getAllLicenses() {
        return licenseRepository.findAll();
    }

    @Cacheable(value = LICENSE_CACHE, key = "#name")
    @Override
    public Optional<License> getLicenseByName(String name) {
        return licenseRepository.getLicenseByName(name);
    }

    @CacheEvict(value = {
            LICENSES_CACHE,
            COST_DATA_OVERVIEW_CACHE,
            AVERAGE_COSTS_OVERVIEW_CACHE}, allEntries = true)
    @CachePut(value = LICENSE_CACHE, key = "#result.id")
    @Override
    public License addLicense(License license) {
        License savedLicense = licenseRepository.save(license);
        List<Credential> credentials = license.getCredentials();
        credentials.forEach(credential -> credential.setLicense(savedLicense));
        credentialRepository.saveAll(credentials);
        return savedLicense;
    }

    @Cacheable(value = LICENSE_CACHE, key = "#id")
    @Override
    public Optional<License> getLicenseById(Integer id) {
        return licenseRepository.findById(id);
    }

    @CacheEvict(value = {
            LICENSES_CACHE,
            COST_DATA_OVERVIEW_CACHE,
            AVERAGE_COSTS_OVERVIEW_CACHE,
            REQUESTS_CACHE,
            REQUEST_CACHE}, allEntries = true)
    @CachePut(value = LICENSE_CACHE, key = "#result.id")
    @Override
    public License updateLicense(License newLicense, License oldLicense) {
        BeanUtils.copyProperties(newLicense, oldLicense, "id", "credentials");
        oldLicense = updateCredentials(newLicense, oldLicense);
        return licenseRepository.save(oldLicense);
    }

    @Override
    public License updateCredentials(License newLicense, License oldLicense) {
        credentialRepository.deleteAll(oldLicense.getCredentials());

        List<Credential> newCredentials = newLicense.getCredentials();
        newCredentials.forEach(credential -> credential.setLicense(oldLicense));
        oldLicense.setCredentials(newCredentials);

        credentialRepository.saveAll(newLicense.getCredentials());
        return oldLicense;
    }

    @Caching(
            evict = {
                    @CacheEvict(value = {
                            LICENSES_CACHE,
                            COST_DATA_OVERVIEW_CACHE,
                            AVERAGE_COSTS_OVERVIEW_CACHE,
                            REQUESTS_CACHE,
                            REQUEST_CACHE}, allEntries = true),
                    @CacheEvict(value = LICENSE_CACHE, key = "#id")
            }
    )
    @Override
    public void deleteById(int id) {
        License license = licenseRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Requested license to delete was not found")
        );
        licenseRepository.delete(license);
    }

    @Override
    public Optional<License> getLicensesByWebsite(String website) {
        return licenseRepository.getLicensesByWebsite(website);
    }

    @Override
    public Optional<License> getLicensesByDescription(String description) {
        return licenseRepository.getLicensesByDescription(description);
    }

    @Override
    public Optional<License> getLicensesByLogo(byte[] logo) {
        return licenseRepository.getLicensesByLogo(logo);
    }

    @Override
    public Optional<Credential> getCredentialByUsername(String username) {
        return credentialRepository.getCredentialByUsername(username);
    }

    @Override
    public Optional<Credential> getCredentialByPassword(String password) {
        return credentialRepository.getCredentialByPassword(password);
    }
}
