package endava.internship.traininglicensessharing.archunit;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import endava.internship.traininglicensessharing.TrainingLicensesSharingApplication;

@AnalyzeClasses(packagesOf = TrainingLicensesSharingApplication.class,
        importOptions = {ImportOption.DoNotIncludeTests.class})
public class LayerDependencyTest {

    @ArchTest
    public static final ArchRule layerDependencyCheck = layeredArchitecture()
            .consideringOnlyDependenciesInLayers()
            .layer("rest").definedBy(resideInAPackage("..rest.."))
            .layer("domain").definedBy(resideInAPackage("..domain.."))
            .layer("application").definedBy(resideInAPackage("..application.."))

            .whereLayer("rest").mayNotBeAccessedByAnyLayer()
            .whereLayer("domain").mayOnlyBeAccessedByLayers("application")
            .whereLayer("application").mayOnlyBeAccessedByLayers("rest");
}