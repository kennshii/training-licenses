package endava.internship.traininglicensessharing.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import endava.internship.traininglicensessharing.TrainingLicensesSharingApplication;

@AnalyzeClasses(packagesOf = TrainingLicensesSharingApplication.class,
        importOptions = ImportOption.DoNotIncludeTests.class)
public class NamingRulesTest {

    @ArchTest
    static final ArchRule interfacesShouldNotHaveNamesEndingWithInterfaceWord =
            noClasses().that().areInterfaces().should().haveNameMatching(".*Interface");

    @ArchTest
    static final ArchRule controllerImplementationsShouldBeSuffixed = classes()
            .that()
            .areAnnotatedWith(RestController.class)
            .should()
            .haveSimpleNameEndingWith("ControllerImpl");

    @ArchTest
    static final ArchRule facadeImplementationsShouldBeSuffixed = classes()
            .that()
            .areNotInterfaces()
            .and()
            .resideInAPackage("..facade..")
            .should()
            .haveSimpleNameEndingWith("Impl");

    @ArchTest
    static final ArchRule dtoShouldContainDtoWord = classes()
            .that()
            .areNotInterfaces()
            .and()
            .resideInAPackage("..application.dto..")
            .and()
            .resideOutsideOfPackage("..application.dto.deserializer..")
            .should()
            .haveSimpleNameContaining("Dto");

    @ArchTest
    static final ArchRule servicesShouldEndWithServiceImpl = classes()
            .that()
            .areAnnotatedWith(Service.class)
            .should()
            .haveSimpleNameEndingWith("Impl");

    @ArchTest
    static final ArchRule repositoryImplementationsShouldEndWithRepository = classes()
            .that()
            .areAnnotatedWith(Repository.class)
            .should()
            .haveSimpleNameEndingWith("Repository");

    @ArchTest
    static final ArchRule validatorsShouldBeSuffixed = classes()
            .that()
            .resideInAPackage("..application.validator..")
            .should()
            .haveSimpleNameEndingWith("Validator");

    @ArchTest
    static final ArchRule mappersShouldBeSuffixed = classes()
            .that().areInterfaces()
            .and().resideInAPackage("..application.mapper..")
            .should()
            .haveSimpleNameContaining("Mapper");
}