package endava.internship.traininglicensessharing.rest.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import endava.internship.traininglicensessharing.application.dto.AverageCostsDto;
import endava.internship.traininglicensessharing.application.dto.LicenseCostDto;
import endava.internship.traininglicensessharing.application.dto.LicenseExpiringDto;
import endava.internship.traininglicensessharing.application.dto.LicenseUnusedDto;
import endava.internship.traininglicensessharing.application.dto.UsersOverviewDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface DashboardController {
    @Operation(summary = "Get expiring licenses", description = "Retrieves all expiring licenses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved expired licenses or returned an empty array",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LicenseExpiringDto.class)))
    })
    ResponseEntity<List<LicenseExpiringDto>> getExpiringLicenses();

    @Operation(summary = "Get unused licenses", description = "Retrieves all unused licenses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved unused licenses or returned an empty array",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LicenseUnusedDto.class)))
    })
    ResponseEntity<List<LicenseUnusedDto>> getUnusedLicenses();

    @Operation(summary = "Get all users",
            description = "Retrieves all users that accessed Training licenses site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved all users or returned an empty array",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UsersOverviewDto.class)))
    })
    ResponseEntity<UsersOverviewDto> getUsersOverview();

    @Operation(summary = "Get average licenses' costs per user",
            description = "Retrieves average licenses' costs per each user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved average licenses' cost per user or returned an empty array",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AverageCostsDto.class)))
    })
    ResponseEntity<AverageCostsDto> getAverageCostsPerUser();

    @Operation(summary = "Get licenses' costs",
            description = "Retrieves costs for all Software/Training licenses per a year")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved licenses' cost per a year or returned an empty array",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LicenseCostDto.class)))
    })
    LicenseCostDto getCostsData();
}