package endava.internship.traininglicensessharing.rest.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import endava.internship.traininglicensessharing.application.dto.LicenseDtoRequest;
import endava.internship.traininglicensessharing.application.dto.LicenseDtoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

public interface LicenseController {

    @Operation(summary = "Add a new license",
            description = "Adds a new license and returns it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Successfully added the new license",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LicenseDtoResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Validation failed because of invalid field values",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject("{\"credentials\": \"Must be between 1 and 10 credentials.\"}")
                            })
                    })
    })
    ResponseEntity<LicenseDtoResponse> addLicense(LicenseDtoRequest licenseDtoRequest, BindingResult bindingResult);

    @Operation(summary = "Get all licenses",
            description = "Returns all licenses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved all licenses or returned an empty array",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LicenseDtoResponse.class))),
    })
    List<LicenseDtoResponse> getAllLicenses();

    @Operation(summary = "Retrieve license by id",
            description = "Retrieves the license according to the specified id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved the license or returned an empty array",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LicenseDtoResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Validation failed because of invalid data",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject("{\"id\": \"License id is invalid\"}")
                            })
                    }),
            @ApiResponse(responseCode = "404",
                    description = "Retrieving failed because license with this id was not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject("{\"id\": \"License with this id was not found\"}")
                            })
                    })
    })
    ResponseEntity<LicenseDtoResponse> getLicenseById(@PathVariable("licenseId")
                                                      @Parameter(name = "licenseId", description = "license id", example = "1") Integer licenseId);

    @Operation(summary = "Update license",
            description = "Updates details for license according to the specified id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Successfully updated the license data",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LicenseDtoResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Update failed because of invalid field values",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject("{\"seatsTotal\": \"Number of seat must not exceed 250.\"}")
                            })
                    }),
            @ApiResponse(responseCode = "404",
                    description = "Update failed because license with this id was not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject("{\"id\": \"License with this id was not found\"}")
                            })
                    })
    })
    ResponseEntity<LicenseDtoResponse> updateLicense(
            @RequestBody @Valid LicenseDtoRequest licenseDtoRequest,
            BindingResult bindingResult,
            @PathVariable("licenseId") @Parameter(name = "licenseId", description = "license id", example = "1")
            Integer licenseId);

    @Operation(summary = "Delete license by id",
            description = "Removes the license according to the specified id and returns it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully deleted the license",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400",
                    description = "License's removal failed because of invalid data provided",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject("{\"id\": \"License id is not valid\"}")
                            })
                    }),
            @ApiResponse(responseCode = "404",
                    description = "Removal failed because license was not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject("{\"id\": \"License with this id was not found\"}")
                            })
                    })
    })
    ResponseEntity<Void> deleteLicenseById(
            @Parameter(name = "licenseId", description = "license id", example = "1") String licenseId);
}