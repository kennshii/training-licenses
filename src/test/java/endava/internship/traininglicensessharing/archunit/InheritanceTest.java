package endava.internship.traininglicensessharing.archunit;

import static com.tngtech.archunit.core.domain.properties.HasName.Predicates.nameEndingWith;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import endava.internship.traininglicensessharing.TrainingLicensesSharingApplication;

@AnalyzeClasses(packagesOf = TrainingLicensesSharingApplication.class,
        importOptions = ImportOption.DoNotIncludeTests.class)
public class InheritanceTest {
    @ArchTest
    static final ArchRule implementationsShouldHaveInterfaces = classes().that()
            .haveSimpleNameEndingWith("Impl")
            .should().implement(nameEndingWith("Controller"))
            .orShould()
            .implement(nameEndingWith("Facade"))
            .orShould()
            .implement(nameEndingWith("Service"))
            .orShould()
            .implement(nameEndingWith("Mapper"));
}