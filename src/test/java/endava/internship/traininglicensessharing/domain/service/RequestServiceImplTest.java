package endava.internship.traininglicensessharing.domain.service;

import static endava.internship.traininglicensessharing.utils.RequestTestUtils.REQUEST;
import static endava.internship.traininglicensessharing.utils.RequestTestUtils.REQUEST_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import endava.internship.traininglicensessharing.domain.entity.Request;
import endava.internship.traininglicensessharing.domain.enums.RequestStatus;
import endava.internship.traininglicensessharing.domain.repository.RequestRepository;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class RequestServiceImplTest {

    @Mock
    private RequestRepository requestRepository;
    @InjectMocks
    private RequestServiceImpl requestService;

    @Test
    void itShouldEditRequest() {
        List<Request> requests = List.of(REQUEST);
        RequestStatus requestStatus = RequestStatus.REJECTED;

        when(requestRepository.saveAll(requests)).thenReturn(requests);

        List<Request> result = requestService.editRequest(requests, requestStatus);

        assertEquals(requests, result);

        for (Request request : requests) {
            assertEquals(requestStatus, request.getStatus());
        }

        verify(requestRepository).saveAll(requests);
    }

    @Test
    void itShouldDeleteRequests() {
        when(requestRepository.findById(REQUEST_ID)).thenReturn(Optional.of(REQUEST));

        requestService.deleteRequests(List.of(REQUEST_ID));

        verify(requestRepository).deleteById(REQUEST_ID);
    }

    @Test
    void itShouldNotDeleteRequests() {
        when(requestRepository.findById(REQUEST_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> requestService.deleteRequests(List.of(REQUEST_ID)));

        verify(requestRepository, never()).deleteById(REQUEST_ID);
    }
}
