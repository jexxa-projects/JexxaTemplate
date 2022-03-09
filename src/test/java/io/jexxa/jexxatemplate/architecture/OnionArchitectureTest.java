package io.jexxa.jexxatemplate.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import io.jexxa.jexxatemplate.JexxaTemplate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static io.jexxa.jexxatemplate.architecture.PackageName.AGGREGATE;
import static io.jexxa.jexxatemplate.architecture.PackageName.APPLICATIONSERVICE;
import static io.jexxa.jexxatemplate.architecture.PackageName.BUSINESS_EXCEPTION;
import static io.jexxa.jexxatemplate.architecture.PackageName.DOMAIN_EVENT;
import static io.jexxa.jexxatemplate.architecture.PackageName.DOMAIN_PROCESS_SERVICE;
import static io.jexxa.jexxatemplate.architecture.PackageName.DOMAIN_SERVICE;
import static io.jexxa.jexxatemplate.architecture.PackageName.INFRASTRUCTURE;
import static io.jexxa.jexxatemplate.architecture.PackageName.VALUE_OBJECT;

class OnionArchitectureTest
{
    private static JavaClasses importedClasses;

    @BeforeAll
    static void initBeforeAll()
    {
        importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages(JexxaTemplate.class.getPackage().getName());
    }

    @Test
    void testPackageStructure() {
        // Arrange -

        // Act
        var rule = classes().should()
                .resideInAnyPackage(
                        APPLICATIONSERVICE,
                        DOMAIN_PROCESS_SERVICE,
                        DOMAIN_SERVICE,
                        AGGREGATE,
                        BUSINESS_EXCEPTION,
                        DOMAIN_EVENT,
                        VALUE_OBJECT,
                        INFRASTRUCTURE)
                .orShould().haveFullyQualifiedName(JexxaTemplate.class.getName());

        //Assert
        rule.check(importedClasses);
    }

    @Test
    void testApplicationServiceDependencies() {
        // Arrange -

        // Act
        var allowedAccess=  classes().that().resideInAPackage(APPLICATIONSERVICE)
                .should().dependOnClassesThat()
                .resideInAnyPackage(DOMAIN_EVENT,
                        VALUE_OBJECT,
                        BUSINESS_EXCEPTION,
                        AGGREGATE,
                        DOMAIN_SERVICE,
                        DOMAIN_PROCESS_SERVICE);

        var invalidAccess = noClasses().that().resideInAPackage(APPLICATIONSERVICE)
                .should().dependOnClassesThat().resideInAnyPackage( APPLICATIONSERVICE,
                        INFRASTRUCTURE);

        //Assert
        allowedAccess.check(importedClasses);
        invalidAccess.check(importedClasses);
    }

}
