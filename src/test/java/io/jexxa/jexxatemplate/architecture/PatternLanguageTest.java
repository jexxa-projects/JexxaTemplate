package io.jexxa.jexxatemplate.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import io.jexxa.addend.applicationcore.Aggregate;
import io.jexxa.addend.applicationcore.ApplicationService;
import io.jexxa.addend.applicationcore.BusinessException;
import io.jexxa.addend.applicationcore.DomainEvent;
import io.jexxa.addend.applicationcore.DomainProcessStep;
import io.jexxa.addend.applicationcore.DomainService;
import io.jexxa.addend.applicationcore.DomainWorkflow;
import io.jexxa.addend.applicationcore.InfrastructureService;
import io.jexxa.addend.applicationcore.Repository;
import io.jexxa.addend.applicationcore.ValueObject;
import io.jexxa.jexxatemplate.JexxaTemplate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

class PatternLanguageTest {

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
        // Arrange
        var applicationService = "..applicationservice";
        var domainProcessService = "..domainprocessservice";
        var domainService = "..domainservice";
        var domainAggregate = "..domain.aggregate";
        var domainBusinessException = "..domain.businessexception";
        var domainDomainEvent = "..domain.domainevent";
        var domainValueObject = "..domain.valueobject";
        var infrastructure = "..infrastructure..";

        // Act
        var rule = classes().should()
                .resideInAnyPackage(
                        applicationService,
                        domainProcessService,
                        domainService,
                        domainAggregate,
                        domainBusinessException,
                        domainDomainEvent,
                        domainValueObject,
                        infrastructure)
                .orShould().haveFullyQualifiedName(JexxaTemplate.class.getName());

        //Assert
        rule.check(importedClasses);
    }

    @Test
    void testAnnotationApplicationService()
    {
        // Arrange

        //Act
        var annotationRule = classes().that().resideInAnyPackage("..applicationservice")
                .should().beAnnotatedWith(ApplicationService.class);

        //Assert
        annotationRule.check(importedClasses);
    }

    @Test
    void testAnnotationDomainService() {
        // Arrange

        //Act
        var annotationRule = classes().that().resideInAnyPackage("..domainservice")
                .should().beAnnotatedWith(Repository.class)
                .orShould().beAnnotatedWith(InfrastructureService.class)
                .orShould().beAnnotatedWith(DomainService.class);

        //Assert
        annotationRule.check(importedClasses);
    }

    @Test
    void testAnnotationDomainProcessService() {
        // Arrange

        //Act
        var annotationRule = classes().that().resideInAnyPackage("..domainprocessservice")
                .should().beAnnotatedWith(DomainProcessStep.class)
                .orShould().beAnnotatedWith(DomainWorkflow.class)
                .allowEmptyShould(true);

        //Assert
        annotationRule.check(importedClasses);
    }

    @Test
    void testAnnotationDomainEvent() {
        // Arrange

        //Act
        var annotationRule = classes().that().resideInAnyPackage("..domain.domainevent")
                .should().beAnnotatedWith(DomainEvent.class)
                .allowEmptyShould(true);

        //Assert
        annotationRule.check(importedClasses);
    }

    @Test
    void testAnnotationValueObject() {
        // Arrange

        //Act
        var annotationRule = classes().that().resideInAnyPackage("..domain.valueobject")
                .should().beAnnotatedWith(ValueObject.class)
                .allowEmptyShould(true);

        //Assert
        annotationRule.check(importedClasses);
    }

    @Test
    void testAnnotationBusinessException() {
        // Arrange

        //Act
        var annotationRule = classes().that().resideInAnyPackage("..domain.businessexception")
                .should().beAnnotatedWith(BusinessException.class)
                .allowEmptyShould(true);

        //Assert
        annotationRule.check(importedClasses);
    }

    @Test
    void testAnnotationAggregate() {
        // Arrange

        //Act
        var annotationRule = classes().that().resideInAnyPackage("..domain.aggregate")
                .should().beAnnotatedWith(Aggregate.class)
                .allowEmptyShould(true);

        //Assert
        annotationRule.check(importedClasses);
    }

}

