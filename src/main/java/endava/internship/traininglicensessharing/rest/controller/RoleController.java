package endava.internship.traininglicensessharing.rest.controller;

import java.util.List;

import org.springframework.http.MediaType;

import endava.internship.traininglicensessharing.application.dto.RoleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


public interface RoleController {
    @Operation(summary = "Get all roles",
            description = "Returns all roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved all roles or returned an empty array",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RoleDto.class))),
    })
    List<RoleDto> getAllRoles();
}
