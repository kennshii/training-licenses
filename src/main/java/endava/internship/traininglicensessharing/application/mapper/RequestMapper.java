package endava.internship.traininglicensessharing.application.mapper;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import endava.internship.traininglicensessharing.application.dto.RequestDtoResponse;
import endava.internship.traininglicensessharing.domain.entity.Request;
import endava.internship.traininglicensessharing.domain.enums.RequestStatus;
import endava.internship.traininglicensessharing.application.exception.ResourceNotFoundException;

@Mapper
public interface RequestMapper {

    @Mapping(target = "requestId", source = "id")
    @Mapping(target = "app", expression = "java(request.getLicense().getName())")
    @Mapping(target = "requestedOn", expression = "java(requestedOnFormat(request))")
    @Mapping(target = "startOfUse", expression = "java(startOfUseFormat(request))")
    @Mapping(target = "user", expression = "java(request.getUser().getFirstName() + \" \" + request.getUser().getLastName())")
    @Mapping(target = "discipline", expression = "java(request.getUser().getWorkplace().getDiscipline().getName())")
    RequestDtoResponse requestToRequestDtoResponse(Request request);

    default String requestedOnFormat(Request request) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm", Locale.ENGLISH);
        return dateTimeFormatter.format(request.getRequestedOn());
    }

    default String startOfUseFormat(Request request) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
        return dateTimeFormatter.format(request.getStartOfUse());
    }

    default RequestStatus requestStatusStrToObj(String request) {
        RequestStatus requestStatus;
        try {
             requestStatus = RequestStatus.valueOf(request);
        }
        catch (IllegalArgumentException e){
            throw new ResourceNotFoundException("RequestStatus with name " + request + " not found");
        }

        return requestStatus;
    }
}
