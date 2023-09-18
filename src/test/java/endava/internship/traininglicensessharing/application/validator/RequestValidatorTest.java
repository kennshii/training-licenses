package endava.internship.traininglicensessharing.application.validator;

import static endava.internship.traininglicensessharing.utils.RequestTestUtils.REQUEST;
import static endava.internship.traininglicensessharing.utils.RequestTestUtils.REQUEST_DTO_REQUEST;
import static endava.internship.traininglicensessharing.utils.RequestTestUtils.REQUEST_ID;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.Errors;

import endava.internship.traininglicensessharing.domain.entity.Request;
import endava.internship.traininglicensessharing.domain.enums.RequestStatus;
import endava.internship.traininglicensessharing.domain.service.RequestService;
@ExtendWith(MockitoExtension.class)
class RequestValidatorTest {

    @Mock
    private RequestService requestService;

    @Mock
    private Errors errors;

    @InjectMocks
    private RequestValidator requestValidator;

    @Test
    void itShouldValidateRequests() {
        Request request = REQUEST;
        request.setStatus(RequestStatus.PENDING);

        when(requestService.getRequestById(REQUEST_ID)).thenReturn(Optional.of(request));

        requestValidator.validate(REQUEST_DTO_REQUEST, errors);

        verify(requestService).getRequestById(REQUEST_ID);
        verify(errors, never()).rejectValue(any(), any(), any());
    }

    @Test
    void itShouldNotValidateNonExistingRequests() {
        when(requestService.getRequestById(REQUEST_ID)).thenReturn(Optional.empty());

        requestValidator.validate(REQUEST_DTO_REQUEST, errors);

        verify(requestService).getRequestById(REQUEST_ID);
        verify(errors).rejectValue("requestIds[0]", "", "Request with id " + REQUEST_ID + " not found");
    }

    @Test
    void itShouldNotValidateRejectedRequests() {
        Request request = REQUEST;
        request.setStatus(RequestStatus.REJECTED);

        when(requestService.getRequestById(REQUEST_ID)).thenReturn(Optional.of(request));

        requestValidator.validate(REQUEST_DTO_REQUEST, errors);

        verify(requestService).getRequestById(REQUEST_ID);
        verify(errors).rejectValue("requestIds[0]", "", "Request with id " + REQUEST_ID + " has rejected status");
    }
}

