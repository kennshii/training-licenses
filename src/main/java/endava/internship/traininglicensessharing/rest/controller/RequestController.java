package endava.internship.traininglicensessharing.rest.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import endava.internship.traininglicensessharing.application.dto.RequestDtoRequest;
import endava.internship.traininglicensessharing.application.dto.RequestDtoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface RequestController {

    @Operation(summary = "Edit requests",
            description = "Updates status for requests according to specified ids")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully changed requests status",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RequestDtoResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Edit status failed because request status is \"REJECTED\" or request with such id doesn't exist",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(value = "{\"requestId[0]\": \"Request with id 1 has rejected status\"}", name = "Rejected status"),
                                    @ExampleObject(value = "{\"requestId[0]\": \"Request with id 1 not found\"}", name = "Id not found")
                            })
                    })
    })
    ResponseEntity<List<RequestDtoResponse>> editRequest(
            @RequestBody RequestDtoRequest requestDtoRequest, BindingResult bindingResult);

    @Operation(summary = "Get all requests",
            description = "Returns all requests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved all requests or returned an empty array",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RequestDtoResponse.class))),
    })
    ResponseEntity<List<RequestDtoResponse>> getAllRequests();

    @Operation(summary = "Delete requests by id",
            description = "Removes requests according to all specified ids")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully deleted requests",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404",
                    description = "Removal failed because request was not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject("{\"Not found\": \"Request with id: 1 was not found\"}")
                            })
                    })
    })
    ResponseEntity<Void> deleteRequest(@RequestBody
                                       @Schema(name = "requestIds", description = "array of ids to delete", example = "[1, 2, 3]") List<Integer> requestIds);
}
