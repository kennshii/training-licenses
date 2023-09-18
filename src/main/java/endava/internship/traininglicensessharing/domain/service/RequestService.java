package endava.internship.traininglicensessharing.domain.service;

import java.util.List;
import java.util.Optional;

import endava.internship.traininglicensessharing.domain.entity.Request;
import endava.internship.traininglicensessharing.domain.enums.RequestStatus;

public interface RequestService {

    Optional<Request> getRequestById(Integer requestId);

    List<Request> editRequest(List<Request> request, RequestStatus requestStatus);

    List<Request> getAllRequests();

    void deleteRequests(List<Integer> requestIds);
}
