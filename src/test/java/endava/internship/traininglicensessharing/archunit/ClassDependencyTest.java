package endava.internship.traininglicensessharing.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import endava.internship.traininglicensessharing.TrainingLicensesSharingApplication;

@AnalyzeClasses(packagesOf = TrainingLicensesSharingApplication.class,
        importOptions = ImportOption.DoNotIncludeTests.class)
public class ClassDependencyTest {

    @ArchTest
    static final ArchRule validatorShouldBeUsedOnlyInFacade = classes()
            .that().haveNameMatching(".*Validator")
            .should().onlyHaveDependentClassesThat()
            .haveSimpleNameEndingWith("FacadeImpl");

    @ArchTest
    static final ArchRule mapperShouldBeUsedOnlyInFacade = classes()
            .that().haveNameMatching(".*MapperImpl")
            .should().onlyHaveDependentClassesThat()
            .haveSimpleNameEndingWith("FacadeImpl");

    @ArchTest
    static final ArchRule facadeShouldBeUsedOnlyInController = classes()
            .that().resideInAPackage("..facade..")
            .should().onlyHaveDependentClassesThat()
            .resideInAnyPackage("..controller", "..facade..");

    @ArchTest
    static final ArchRule serviceShouldBeUsedOnlyInFacadeOrOtherServices = classes()
            .that().resideInAPackage("..service..")
            .should().onlyHaveDependentClassesThat()
            .resideInAnyPackage("..service..", "..facade..", "..validator..");

    @ArchTest
    static final ArchRule repositoryShouldBeUsedOnlyInServiceImplOrInValidator = classes()
            .that()
            .haveSimpleNameEndingWith("Repository")
            .should().onlyHaveDependentClassesThat()
            .resideInAnyPackage("..service..", "..validator..");
}