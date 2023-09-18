package endava.internship.traininglicensessharing.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import endava.internship.traininglicensessharing.TrainingLicensesSharingApplication;
import jakarta.persistence.Entity;

@AnalyzeClasses(packagesOf = TrainingLicensesSharingApplication.class,
        importOptions = ImportOption.DoNotIncludeTests.class)
public class ClassAndPackageContainmentTest {

    @ArchTest
    static final ArchRule facadeShouldBeLocatedInFacadePackage = classes()
            .that().haveSimpleNameEndingWith("FacadeImpl")
            .or().haveSimpleNameContaining("Facade").and().areInterfaces()
            .should().resideInAPackage("..application.facade");

    @ArchTest
    static final ArchRule mapperShouldBeLocatedInMapperPackage = classes()
            .that().haveSimpleNameEndingWith("Mapper")
            .and().areInterfaces()
            .should().resideInAPackage("..application.mapper");

    @ArchTest
    static final ArchRule validatorShouldBeLocatedInValidatorPackage = classes()
            .that().haveSimpleNameEndingWith("Validator")
            .should().resideInAPackage("..application.validator");

    @ArchTest
    static final ArchRule applicationExceptionsShouldBeLocatedInApplicationPackage = classes()
            .that().resideInAPackage("..exception..")
            .should().resideInAPackage("..application..");

    @ArchTest
    static final ArchRule dtoShouldBeLocatedInApplicationPackage = classes()
            .that().haveSimpleNameContaining("Dto")
            .should().resideInAPackage("..application.dto..");

    @ArchTest
    static final ArchRule repositoryShouldBeLocatedInRepositoryPackage = classes()
            .that().haveSimpleNameEndingWith("Repository")
            .and().areInterfaces()
            .should().resideInAPackage("..domain.repository");

    @ArchTest
    static final ArchRule entitiesMustResideInDomainPackage = classes()
            .that().areAnnotatedWith(Entity.class)
            .should().resideInAPackage("..entity..")
            .as("Entities should reside in a package '..domain.entity..'");

    @ArchTest
    static final ArchRule enumsShouldBeLocatedInDomainPackage = classes()
            .that().resideInAPackage("..enums..")
            .should().resideInAPackage("..domain..");

    static final ArchRule serviceMustResideInDomainPackage = classes()
            .that().haveSimpleNameContaining("ServiceImpl")
            .or().haveSimpleNameContaining("Service").and().areInterfaces()
            .should().resideInAPackage("..domain.service..");

    @ArchTest
    static final ArchRule controllersShouldBeLocatedInRestPackage = classes()
            .that().haveSimpleNameEndingWith("ControllerImpl")
            .or().haveSimpleNameContaining("Controller").and().areInterfaces()
            .should().resideInAPackage("..rest.controller");

    @ArchTest
    static final ArchRule adviceShouldBeLocatedInRestPackage = classes()
            .that().resideInAPackage("..advice..")
            .should().resideInAPackage("..rest..");
}