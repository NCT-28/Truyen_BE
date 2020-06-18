package vn.com.truyen.usermanagent;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("vn.com.truyen.usermanagent");

        noClasses()
            .that()
                .resideInAnyPackage("vn.com.truyen.usermanagent.service..")
            .or()
                .resideInAnyPackage("vn.com.truyen.usermanagent.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..vn.com.truyen.usermanagent.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
