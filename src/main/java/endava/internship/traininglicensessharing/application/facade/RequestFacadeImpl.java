package endava.internship.traininglicensessharing.application.facade;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import endava.internship.traininglicensessharing.application.dto.RequestDtoRequest;
import endava.internship.traininglicensessharing.application.dto.RequestDtoResponse;
import endava.internship.traininglicensessharing.application.exception.ResourceNotFoundException;
import endava.internship.traininglicensessharing.application.exception.ValidationCustomException;
import endava.internship.traininglicensessharing.application.mapper.RequestMapper;
import endava.internship.traininglicensessharing.application.validator.RequestValidator;
import endava.internship.traininglicensessharing.domain.entity.Request;
import endava.internship.traininglicensessharing.domain.enums.RequestStatus;
import endava.internship.traininglicensessharing.domain.service.RequestService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RequestFacadeImpl implements RequestFacade {

    private final RequestService requestService;
    private final RequestMapper requestMapper;
    private final RequestValidator validator;

    @Override
    public List<RequestDtoResponse> editRequest(RequestDtoRequest requestDto, BindingResult bindingResult) {

        validator.validate(requestDto, bindingResult);

        if (bindingResult.hasErrors())
            throw new ValidationCustomException(bindingResult);

        List<Request> requests = requestDto.getRequestIds().stream()
                .map(id -> requestService.getRequestById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Request with id " + id + " not found")))
                .toList();

        RequestStatus requestStatus = requestMapper.requestStatusStrToObj(requestDto.getStatus());

        return requestService.editRequest(requests, requestStatus).stream()
                .map(requestMapper::requestToRequestDtoResponse)
                .toList();
    }

    @Override
    public List<RequestDtoResponse> getAllRequests() {
        return requestService.getAllRequests().stream().map(requestMapper::requestToRequestDtoResponse).toList();
    }

    @Override
    public void deleteRequest(List<Integer> requestIds) {
        requestService.deleteRequests(requestIds);
    }
}
