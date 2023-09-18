package endava.internship.traininglicensessharing.application.mapper;

import static endava.internship.traininglicensessharing.utils.RequestTestUtils.REQUEST;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import endava.internship.traininglicensessharing.application.dto.RequestDtoResponse;
import endava.internship.traininglicensessharing.application.exception.ResourceNotFoundException;
import endava.internship.traininglicensessharing.domain.entity.Request;
import endava.internship.traininglicensessharing.domain.enums.RequestStatus;
import endava.internship.traininglicensessharing.utils.RequestTestUtils;

class RequestMapperTest {

    private final RequestMapper requestMapper = Mappers.getMapper(RequestMapper.class);

    @Test
    void itShouldMapRequestToDtoResponse() {
        Request request = REQUEST;
        request.setStatus(RequestStatus.PENDING);

        RequestDtoResponse requestDtoResponse = requestMapper.requestToRequestDtoResponse(request);
        assertThat(requestDtoResponse).isEqualTo(RequestTestUtils.REQUEST_DTO_RESPONSE);
    }

    @Test
    void itShouldMapRequestStatus() {
        String validRequest = "APPROVED";
        RequestStatus result = requestMapper.requestStatusStrToObj(validRequest);

        assertEquals(RequestStatus.APPROVED, result);
    }

    @Test
    void itShouldNotMapRequestStatus() {
        String invalidRequest = "INVALID_STATUS";
        assertThrows(ResourceNotFoundException.class, () -> requestMapper.requestStatusStrToObj(invalidRequest));
    }
}
