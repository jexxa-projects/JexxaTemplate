package io.jexxa.jexxatemplate.architecture;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import io.jexxa.addend.applicationcore.Aggregate;
import io.jexxa.addend.applicationcore.Repository;
import io.jexxa.jexxatemplate.JexxaTemplate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAnyPackage;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static io.jexxa.jexxatemplate.architecture.PackageName.*;

class StatelessApplicationCoreTest {
    private static JavaClasses importedClasses;

    @BeforeAll
    static void initBeforeAll() {
        importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages(JexxaTemplate.class.getPackage().getName());
    }

    @Test
    void testOnlyRepositoriesReturnAggregates() {
        // Arrange -

        // Act
        var invalidReturnType = noMethods().that()
                .areDeclaredInClassesThat(resideInAnyPackage(APPLICATIONSERVICE, DOMAIN_PROCESS_SERVICE, DOMAIN_SERVICE))
                .and().areDeclaredInClassesThat().areNotAnnotatedWith(Repository.class)
                .should().haveRawReturnType(resideInAnyPackage(AGGREGATE))
                .because("Aggregates contain the business logic and can only be returned by a Repository!");


        //Assert
        invalidReturnType.check(importedClasses);
    }

    @Test
    void testOnlyRepositoriesAcceptAggregates() {
        // Arrange -

        // Act
        var invalidReturnType = noMethods().that()
                .areDeclaredInClassesThat(resideInAnyPackage(APPLICATIONSERVICE, DOMAIN_PROCESS_SERVICE, DOMAIN_SERVICE))
                .and().areDeclaredInClassesThat().areNotAnnotatedWith(Repository.class)
                .should().haveRawParameterTypes(thatAreAggregates())
                .because("Aggregates contain the business logic and can only be returned by a Repository!");


        //Assert
        invalidReturnType.check(importedClasses);
    }


    @Test
    void testFinalFields() {
        // Arrange -

        // Act
        var finalFields = fields().that().areDeclaredInClassesThat()
                .resideInAnyPackage(APPLICATIONSERVICE, DOMAIN_PROCESS_SERVICE, BUSINESS_EXCEPTION, DOMAIN_SERVICE, VALUE_OBJECT)
                .and().areDeclaredInClassesThat().areNotNestedClasses()
                .should().beFinal()
                .because("The application core must be stateless except of Aggregates!");


        //Assert
        finalFields.check(importedClasses);
    }

    @Test
    void testApplicationCoreDoesNotHaveStatefulFields() {
        // Arrange -

        // Act
        var invalidReturnType = noFields().that().areDeclaredInClassesThat()
                .resideInAnyPackage(APPLICATIONSERVICE, DOMAIN_PROCESS_SERVICE, BUSINESS_EXCEPTION, DOMAIN_SERVICE, VALUE_OBJECT)
                .should().haveRawType(resideInAPackage(AGGREGATE))
                .because("An ApplicationService or DomainProcessService must not keep a reference to an aggregate!");


        //Assert
        invalidReturnType.check(importedClasses);
    }

    private static DescribedPredicate<List<JavaClass>> thatAreAggregates() {
        return new DescribedPredicate<>("one parameter of type is an Aggregate") {
            @Override
            public boolean apply(List<JavaClass> input) {
                return input.stream().anyMatch(element -> element.isAnnotatedWith(Aggregate.class));
            }
        };
    }
}