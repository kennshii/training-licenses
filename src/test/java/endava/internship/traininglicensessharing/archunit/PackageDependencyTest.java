package endava.internship.traininglicensessharing.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.DependencyRules.NO_CLASSES_SHOULD_DEPEND_UPPER_PACKAGES;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import endava.internship.traininglicensessharing.TrainingLicensesSharingApplication;

@AnalyzeClasses(packagesOf = TrainingLicensesSharingApplication.class,
        importOptions = ImportOption.DoNotIncludeTests.class)
public class PackageDependencyTest {

    @ArchTest
    static final ArchRule controllersShouldAccessFacade = classes()
            .that()
            .resideInAPackage("..controller..")
            .should()
            .accessClassesThat()
            .resideInAPackage("..facade..");

    @ArchTest
    static final ArchRule controllersShouldNotAccessServiceAndRepository = noClasses()
            .that()
            .resideInAPackage("..controller..")
            .should()
            .accessClassesThat()
            .resideInAPackage("..service..")
            .andShould()
            .accessClassesThat()
            .resideInAPackage("..repository..");

    @ArchTest
    static final ArchRule facadeShouldAccessMapperOrValidator = classes()
            .that()
            .resideInAPackage("..facade..")
            .should()
            .accessClassesThat()
            .resideInAPackage("..mapper..")
            .orShould()
            .accessClassesThat()
            .resideInAPackage("..validator..");

    @ArchTest
    static final ArchRule facadeShouldAccessService = classes()
            .that()
            .resideInAPackage("..facade..")
            .should()
            .accessClassesThat()
            .resideInAPackage("..service..");

    @ArchTest
    static final ArchRule facadesShouldNotAccessRepository = noClasses()
            .that()
            .resideInAPackage("..facade..")
            .should()
            .accessClassesThat()
            .resideInAPackage("..repository..");

    @ArchTest
    static final ArchRule serviceShouldAccessRepository = classes()
            .that()
            .resideInAPackage("..service..")
            .should()
            .accessClassesThat()
            .resideInAPackage("..repository..");

    @ArchTest
    static final ArchRule serviceShouldNotAccessRestAndApplication = noClasses()
            .that()
            .resideInAPackage("..service..")
            .should()
            .accessClassesThat()
            .resideInAPackage("..rest..")
            .orShould()
            .accessClassesThat()
            .resideInAPackage("..application..");

    @ArchTest
    static final ArchRule repositoryShouldDependOnlyOnEntities = classes()
            .that()
            .resideInAPackage("..repository..")
            .should()
            .dependOnClassesThat()
            .resideInAPackage("..entity..");

    @ArchTest
    static final ArchRule repositoryShouldNotDependOnService = noClasses()
            .that()
            .resideInAPackage("..repository..")
            .should()
            .dependOnClassesThat()
            .resideInAPackage("..service..");

    @ArchTest
    static final ArchRule repositoryShouldNotAccessController = noClasses()
            .that()
            .resideInAPackage("..repository..")
            .should()
            .accessClassesThat()
            .resideInAPackage("..controller..");

    @ArchTest
    static final ArchRule noAccessesToUpperPackage = NO_CLASSES_SHOULD_DEPEND_UPPER_PACKAGES;
}