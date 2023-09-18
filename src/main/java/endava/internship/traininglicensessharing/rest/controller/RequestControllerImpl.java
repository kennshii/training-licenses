package endava.internship.traininglicensessharing.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import endava.internship.traininglicensessharing.application.dto.RequestDtoRequest;
import endava.internship.traininglicensessharing.application.dto.RequestDtoResponse;
import endava.internship.traininglicensessharing.application.facade.RequestFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
@Tag(name = "Requests")
@CrossOrigin
class RequestControllerImpl implements RequestController {

    private final RequestFacade requestFacade;

    @Override
    @PutMapping
    public ResponseEntity<List<RequestDtoResponse>> editRequest(
            @Valid @RequestBody RequestDtoRequest requestDtoRequest, BindingResult bindingResult) {
        return new ResponseEntity<>(requestFacade.editRequest(requestDtoRequest, bindingResult), HttpStatus.OK);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<RequestDtoResponse>> getAllRequests(){
        return new ResponseEntity<>(requestFacade.getAllRequests(), HttpStatus.OK);
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Void> deleteRequest(@RequestBody List<Integer> requestIds){
        requestFacade.deleteRequest(requestIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
