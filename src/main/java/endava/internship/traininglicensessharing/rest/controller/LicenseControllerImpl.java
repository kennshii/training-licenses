package endava.internship.traininglicensessharing.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import endava.internship.traininglicensessharing.application.dto.LicenseDtoRequest;
import endava.internship.traininglicensessharing.application.dto.LicenseDtoResponse;
import endava.internship.traininglicensessharing.application.facade.LicenseFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/licenses")
@RequiredArgsConstructor
@Tag(name = "License")
@CrossOrigin
class LicenseControllerImpl implements LicenseController {

    private final LicenseFacade licenseFacade;

    @Override
    @PostMapping
    public ResponseEntity<LicenseDtoResponse> addLicense(@RequestBody @Valid LicenseDtoRequest licenseDtoRequest,
                                                         BindingResult bindingResult) {
        LicenseDtoResponse response = licenseFacade.addLicense(licenseDtoRequest, bindingResult);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    @GetMapping
    public List<LicenseDtoResponse> getAllLicenses() {
        return licenseFacade.getAllLicences();
    }

    @Override
    @GetMapping("{licenseId}")
    public ResponseEntity<LicenseDtoResponse> getLicenseById(@PathVariable("licenseId") Integer licenseId) {
        LicenseDtoResponse licenseDtoResponse = licenseFacade.getLicenseById(licenseId);
        return new ResponseEntity<>(licenseDtoResponse, HttpStatus.OK);
    }

    @Override
    @PutMapping("{licenseId}")
    public ResponseEntity<LicenseDtoResponse> updateLicense(
        @RequestBody @Valid LicenseDtoRequest licenseDtoRequest,
        BindingResult bindingResult,
        @PathVariable("licenseId") Integer licenseId) {

        LicenseDtoResponse response = licenseFacade.updateLicense(licenseDtoRequest, licenseId, bindingResult);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{licenseId}")
    public ResponseEntity<Void> deleteLicenseById(@PathVariable String licenseId) {
        licenseFacade.deleteLicenseById(licenseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}