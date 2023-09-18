package endava.internship.traininglicensessharing.domain.service;

import static endava.internship.traininglicensessharing.domain.cache.CacheContext.ROLES_CACHE;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import endava.internship.traininglicensessharing.domain.entity.Role;
import endava.internship.traininglicensessharing.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Cacheable(value = ROLES_CACHE)
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

}
