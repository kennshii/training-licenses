package endava.internship.traininglicensessharing.application.facade;

import java.util.List;

import endava.internship.traininglicensessharing.application.dto.AverageCostsDto;
import endava.internship.traininglicensessharing.application.dto.LicenseCostDto;
import endava.internship.traininglicensessharing.application.dto.LicenseExpiringDto;
import endava.internship.traininglicensessharing.application.dto.LicenseUnusedDto;
import endava.internship.traininglicensessharing.application.dto.UsersOverviewDto;

public interface DashboardFacade {

    UsersOverviewDto getUsersOverview();

    AverageCostsDto getAverageCostsDto();

    LicenseCostDto getCostsData();

    List<LicenseExpiringDto> getExpiredLicenses();

    List<LicenseUnusedDto> getUnusedLicenses();

}
