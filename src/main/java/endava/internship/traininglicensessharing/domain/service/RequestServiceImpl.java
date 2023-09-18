package endava.internship.traininglicensessharing.domain.service;

import static endava.internship.traininglicensessharing.domain.cache.CacheContext.REQUESTS_CACHE;
import static endava.internship.traininglicensessharing.domain.cache.CacheContext.REQUEST_CACHE;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import endava.internship.traininglicensessharing.domain.entity.Request;
import endava.internship.traininglicensessharing.domain.enums.RequestStatus;
import endava.internship.traininglicensessharing.domain.repository.RequestRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    @Cacheable(value = REQUEST_CACHE, key = "#requestId")
    @Override
    public Optional<Request> getRequestById(Integer requestId) {
        return requestRepository.findById(requestId);
    }

    @CacheEvict(value = {REQUESTS_CACHE, REQUEST_CACHE}, allEntries = true)
    @Override
    public List<Request> editRequest(List<Request> requests, RequestStatus requestStatus) {
        requests = requests.stream().peek(request -> request.setStatus(requestStatus)).toList();
        return requestRepository.saveAll(requests);
    }

    @Cacheable(value = REQUESTS_CACHE, keyGenerator = "cacheKeyGenerator")
    @Override
    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    @CacheEvict(value = {REQUESTS_CACHE, REQUEST_CACHE}, allEntries = true)
    @Override
    public void deleteRequests(List<Integer> requestIds) {
        requestIds.forEach(id -> {
            Request request = requestRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Request with id: " + id + " was not found")
                );
            requestRepository.deleteById(request.getId());
        });
    }
}
