package endava.internship.traininglicensessharing.application.facade;

import java.util.List;

import org.springframework.validation.BindingResult;

import endava.internship.traininglicensessharing.application.dto.RequestDtoRequest;
import endava.internship.traininglicensessharing.application.dto.RequestDtoResponse;

public interface RequestFacade {
    List<RequestDtoResponse> editRequest(RequestDtoRequest requestDto, BindingResult bindingResult);

    List<RequestDtoResponse> getAllRequests();

    void deleteRequest(List<Integer> requestIds);
}
