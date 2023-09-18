package endava.internship.traininglicensessharing.rest.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import endava.internship.traininglicensessharing.application.dto.UserAdminDto;
import endava.internship.traininglicensessharing.application.dto.UserDisableDto;
import endava.internship.traininglicensessharing.application.dto.UserListAndRoleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface UserAdminController {

    @Operation(summary = "Get all users",
            description = "Displays all users who have accessed the Training Resources site at least 1 time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved all users or returned an empty array",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserAdminDto.class))),
    })
    List<UserAdminDto> getAll();

    @Operation(summary = "Disable users",
            description = "Disable one or multiple users by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully disabled users",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserDisableDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "Disabling failed because of invalid id(s)")
    })
    ResponseEntity<Void> disableUser(@Parameter(name = "userDisableDto", description = "A DTO that contains a list of users' ids to be disabled", example = "{\"userIdList\": [1,2,3,4,5]}") UserDisableDto userDisableDto);

    @Operation(summary = "Modify roles",
            description = "Modify users roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully modified roles for users",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserListAndRoleDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "Failed to change users' roles because of invalid user/role id(s)")
    })
    ResponseEntity<Void> modifyUserRole(@Parameter(name = "userListAndRoleDto", description = "A DTO that contains a list of users' ids and role id to be assigned", example = "{\"userIdList\": [1,2,3,4,5], \n\"roleId\": 5}") UserListAndRoleDto userListAndRoleDto);
}