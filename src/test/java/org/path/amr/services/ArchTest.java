package org.path.amr.services;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("org.path.amr.services");

        noClasses()
            .that()
            .resideInAnyPackage("org.path.amr.services.service..")
            .or()
            .resideInAnyPackage("org.path.amr.services.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..org.path.amr.services.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
