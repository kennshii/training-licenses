package endava.internship.traininglicensessharing.application.validator;

import java.util.Optional;

import org.hibernate.internal.util.MutableInteger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import endava.internship.traininglicensessharing.application.dto.RequestDtoRequest;
import endava.internship.traininglicensessharing.domain.entity.Request;
import endava.internship.traininglicensessharing.domain.enums.RequestStatus;
import endava.internship.traininglicensessharing.domain.service.RequestService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RequestValidator {

    private final RequestService requestService;

    public void validate(RequestDtoRequest requestDto, Errors errors) {
        MutableInteger index = new MutableInteger(0);
        requestDto.getRequestIds()
                .forEach(id -> {
                    validateSingleRequest("requestIds[" + index.get() + "]", id, errors);
                    index.incrementAndGet();
                });
    }

    private void validateSingleRequest(String fieldName, Integer id, Errors errors) {
        Optional<Request> requestOptional = requestService.getRequestById(id);

        if (requestOptional.isEmpty())
            errors.rejectValue(fieldName, "", "Request with id " + id + " not found");
        else if (requestOptional.get().getStatus().equals(RequestStatus.REJECTED))
            errors.rejectValue(fieldName, "", "Request with id " + id + " has rejected status");
    }
}
