package endava.internship.traininglicensessharing.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import endava.internship.traininglicensessharing.application.dto.AverageCostsDto;
import endava.internship.traininglicensessharing.application.dto.LicenseCostDto;
import endava.internship.traininglicensessharing.application.dto.LicenseExpiringDto;
import endava.internship.traininglicensessharing.application.dto.LicenseUnusedDto;
import endava.internship.traininglicensessharing.application.dto.UsersOverviewDto;
import endava.internship.traininglicensessharing.application.facade.DashboardFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/dashboard")
@RestController
@Tag(name = "Dashboard")
@CrossOrigin
class DashboardControllerImpl implements DashboardController {

    private final DashboardFacade dashboardFacade;

    @Override
    @GetMapping("/licenses/expiring")
    public ResponseEntity<List<LicenseExpiringDto>> getExpiringLicenses() {
        return new ResponseEntity<>(dashboardFacade.getExpiredLicenses(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/licenses/unused")
    public ResponseEntity<List<LicenseUnusedDto>> getUnusedLicenses() {
        return new ResponseEntity<>(dashboardFacade.getUnusedLicenses(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/user")
    public ResponseEntity<UsersOverviewDto> getUsersOverview() {
        return new ResponseEntity<>(dashboardFacade.getUsersOverview(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/user/average")
    public ResponseEntity<AverageCostsDto> getAverageCostsPerUser() {
        return new ResponseEntity<>(dashboardFacade.getAverageCostsDto(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/costs/licenseCost")
    public LicenseCostDto getCostsData() {
        return dashboardFacade.getCostsData();
    }
}