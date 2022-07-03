package io.jexxa.jexxatemplate.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import io.jexxa.jexxatemplate.JexxaTemplate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static io.jexxa.jexxatemplate.architecture.PackageName.*;

/**
 * These tests validate the access direction af an onion architecture which is as follows:
 *
 * @startuml
 *
 * package ApplicationCore  #DDDDDD {
 *   [ApplicationService]
 *   [DomainProcessService] <<Optional>>
 *   [DomainService]
 *   [Domain]
 * }
 *
 * [ApplicationService] -down-> [DomainProcessService]
 * [ApplicationService] -down-> [DomainService]
 * [ApplicationService] -down-> [Domain]
 * [DomainProcessService] -down-> [DomainService]
 * [DomainProcessService] -down-> [Domain]
 * [DomainService] -r-> [Domain]
 *
 * @enduml
 * ....
 */
class OnionArchitectureTest {
    private static JavaClasses importedClasses;

    @BeforeAll
    static void initBeforeAll() {
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
        var invalidAccess = noClasses()
                .that().resideInAPackage(APPLICATIONSERVICE)
                .should().dependOnClassesThat()
                .resideInAnyPackage(APPLICATIONSERVICE, INFRASTRUCTURE)
                .allowEmptyShould(true)
                .because("An ApplicationService must not depend on other ApplicationServices or the infrastructure");

        //Assert
        invalidAccess.check(importedClasses);
    }


    @Test
    void testDomainProcessServiceDependencies() {
        // Arrange -

        // Act
        var invalidAccess = noClasses()
                .that().resideInAPackage(DOMAIN_PROCESS_SERVICE)
                .should().dependOnClassesThat()
                .resideInAnyPackage(APPLICATIONSERVICE, INFRASTRUCTURE)
                .allowEmptyShould(true)
                .because("A DomainProcessService must not depend on an ApplicationServices or the infrastructure");


        //Assert
        invalidAccess.check(importedClasses);
    }

    @Test
    void testAggregateDependencies() {
        // Arrange -

        // Act
        var invalidAccess = noClasses()
                .that().resideInAPackage(AGGREGATE)
                .should().dependOnClassesThat()
                .resideInAnyPackage(APPLICATIONSERVICE,
                        DOMAIN_SERVICE,
                        DOMAIN_PROCESS_SERVICE,
                        INFRASTRUCTURE)
                .because("An Aggregate must not depend on any Service or the infrastructure");

        //Assert
        invalidAccess.check(importedClasses);
    }

    @Test
    void testValueObjectDependencies() {
        // Arrange -

        // Act
        var invalidAccess = noClasses()
                .that().resideInAPackage(VALUE_OBJECT)
                .should().dependOnClassesThat()
                .resideInAnyPackage(APPLICATIONSERVICE,
                        DOMAIN_SERVICE,
                        DOMAIN_PROCESS_SERVICE,
                        INFRASTRUCTURE,
                        AGGREGATE,
                        DOMAIN_EVENT,
                        BUSINESS_EXCEPTION)
                .because("A ValueObject must not depend on any other classes of the application except of ValueObjects");


        //Assert
        invalidAccess.check(importedClasses);
    }

    @Test
    void testDomainEventDependencies() {
        // Arrange -

        // Act
        var invalidAccess = noClasses()
                .that().resideInAPackage(DOMAIN_EVENT)
                .should().dependOnClassesThat()
                .resideInAnyPackage(
                        APPLICATIONSERVICE,
                        BUSINESS_EXCEPTION,
                        AGGREGATE,
                        DOMAIN_SERVICE,
                        DOMAIN_PROCESS_SERVICE,
                        INFRASTRUCTURE);

        //Assert
        invalidAccess.check(importedClasses);
    }

    @Test
    void testDrivingAdapterDependencies() {
        // Arrange -

        // Act
        var invalidAccess = noClasses()
                .that().resideInAPackage(DRIVING_ADAPTER)
                .should().dependOnClassesThat()
                .resideInAnyPackage(DRIVEN_ADAPTER)
                .allowEmptyShould(true);

        //Assert
        invalidAccess.check(importedClasses);
    }

    @Test
    void testDrivenAdapterDependencies() {
        // Arrange -

        // Act
        var invalidAccess = noClasses()
                .that().resideInAPackage(DRIVEN_ADAPTER)
                .should().dependOnClassesThat()
                .resideInAnyPackage(DRIVING_ADAPTER,
                        APPLICATIONSERVICE,
                        DOMAIN_PROCESS_SERVICE
                        );

        //Assert
        invalidAccess.check(importedClasses);
    }

}