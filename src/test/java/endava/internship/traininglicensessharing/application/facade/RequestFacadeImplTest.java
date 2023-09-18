package endava.internship.traininglicensessharing.application.facade;

import static endava.internship.traininglicensessharing.utils.RequestTestUtils.EDITED_REQUEST;
import static endava.internship.traininglicensessharing.utils.RequestTestUtils.EDITED_REQUEST_DTO_RESPONSE;
import static endava.internship.traininglicensessharing.utils.RequestTestUtils.REQUEST;
import static endava.internship.traininglicensessharing.utils.RequestTestUtils.REQUEST_DTO_REQUEST;
import static endava.internship.traininglicensessharing.utils.RequestTestUtils.REQUEST_DTO_RESPONSE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;

import endava.internship.traininglicensessharing.application.dto.RequestDtoResponse;
import endava.internship.traininglicensessharing.application.exception.ValidationCustomException;
import endava.internship.traininglicensessharing.application.mapper.RequestMapper;
import endava.internship.traininglicensessharing.application.validator.RequestValidator;
import endava.internship.traininglicensessharing.domain.entity.Request;
import endava.internship.traininglicensessharing.domain.enums.RequestStatus;
import endava.internship.traininglicensessharing.domain.service.RequestService;

@ExtendWith(MockitoExtension.class)
class RequestFacadeImplTest {

    @InjectMocks
    private RequestFacadeImpl requestFacade;

    @Mock
    private RequestMapper requestMapper;

    @Mock
    private RequestService requestService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RequestValidator validator;

    @Test
    void itShoulEditRequest() {
        final List<RequestDtoResponse> response = Collections.singletonList(EDITED_REQUEST_DTO_RESPONSE);

        when(requestService.getRequestById(any(Integer.class))).thenReturn(Optional.ofNullable(REQUEST));
        when(requestMapper.requestToRequestDtoResponse(any(Request.class))).thenReturn(EDITED_REQUEST_DTO_RESPONSE);
        when(requestMapper.requestStatusStrToObj(any(String.class))).thenReturn(Objects.requireNonNull(REQUEST).getStatus());
        when(requestService.editRequest(any(List.class), any(RequestStatus.class)))
                .thenReturn(Collections.singletonList(EDITED_REQUEST));

        List<RequestDtoResponse> actualResponses = requestFacade.editRequest(REQUEST_DTO_REQUEST, bindingResult);
        assertThat(actualResponses).isEqualTo(response);
    }

    @Test
    void itShouldNotEditRequest() {

        when(bindingResult.hasErrors()).thenReturn(true);

        assertThrows(ValidationCustomException.class, () -> requestFacade.editRequest(REQUEST_DTO_REQUEST, bindingResult));

        verify(validator).validate(REQUEST_DTO_REQUEST, bindingResult);
    }

    @Test
    void itShouldGetAllRequests() {

        when(requestService.getAllRequests()).thenReturn(List.of(REQUEST));
        when(requestMapper.requestToRequestDtoResponse(any())).thenReturn(REQUEST_DTO_RESPONSE);

        List<RequestDtoResponse> result = requestFacade.getAllRequests();

        assertEquals(List.of(REQUEST_DTO_RESPONSE), result);

        verify(requestService).getAllRequests();
    }

    @Test
    void itShouldDeleteRequest() {
        List<Integer> requestIds = Arrays.asList(1, 2, 3);

        requestFacade.deleteRequest(requestIds);

        verify(requestService).deleteRequests(requestIds);
    }

}
