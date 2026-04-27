package mocka.odm.generatorTest;

import mocka.core.GenerateType;
import mocka.core.exception.GeneratorException;
import mocka.odm.MockaOdmTestApplication;
import mocka.odm.document.association.Child;
import mocka.odm.document.association.GrandParent;
import mocka.odm.document.association.Parent;
import mocka.odm.generator.DocumentGenerator;
import mocka.odm.generator.DocumentGeneratorFactory;
import mocka.odm.generator.ODMType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest(classes = MockaOdmTestApplication.class)
@DisplayName("DocumentGeneratorFactory Test")
class DocumentGeneratorFactoryTest {

    @Autowired
    private DocumentGeneratorFactory generatorFactory;

    @Test
    @DisplayName("getGenerator with unregistered class throws GeneratorException")
    void getGenerator_unregisteredClass_throwsGeneratorException() {
        assertThatThrownBy(() -> generatorFactory.getGenerator(String.class))
                .isInstanceOf(GeneratorException.class)
                .hasMessageContaining("Cannot find document class");
    }

    @Test
    @DisplayName("DocumentGenerator with GenerateType.CHILD generates children but not grandParent")
    void documentGenerator_withChildType_generatesOnlyChildren() {
        DocumentGenerator<Parent> generator = generatorFactory.getGenerator(Parent.class);

        Parent parent = generator
                .generateType(GenerateType.CHILD)
                .odmType(ODMType.MONGODB)
                .get();

        assertThat(parent).isNotNull();
        assertThat(parent.getChildren()).isNotNull().isNotEmpty();
        assertThat(parent.getGrandParent()).isNull();
    }

    @Test
    @DisplayName("DocumentGenerator with GenerateType.PARENT generates grandParent but not children")
    void documentGenerator_withParentType_generatesOnlyParent() {
        DocumentGenerator<Parent> generator = generatorFactory.getGenerator(Parent.class);

        Parent parent = generator
                .generateType(GenerateType.PARENT)
                .odmType(ODMType.MONGODB)
                .get();

        assertThat(parent).isNotNull();
        assertThat(parent.getGrandParent()).isNotNull().isInstanceOf(GrandParent.class);
        assertThat(parent.getChildren()).isNull();
    }

    @Test
    @DisplayName("DocumentGenerator with GenerateType.CHILDREN generates children recursively")
    void documentGenerator_withChildrenType_generatesChildrenRecursively() {
        DocumentGenerator<Parent> generator = generatorFactory.getGenerator(Parent.class);

        Parent parent = generator
                .generateType(GenerateType.CHILDREN)
                .odmType(ODMType.MONGODB)
                .get();

        assertThat(parent).isNotNull();
        assertThat(parent.getChildren()).isNotNull().isNotEmpty();
        assertThat(parent.getGrandParent()).isNull();
    }

    @Test
    @DisplayName("DocumentGenerator with GenerateType.PARENTS generates ancestors recursively")
    void documentGenerator_withParentsType_generatesAncestorsRecursively() {
        DocumentGenerator<Parent> generator = generatorFactory.getGenerator(Parent.class);

        Parent parent = generator
                .generateType(GenerateType.PARENTS)
                .odmType(ODMType.MONGODB)
                .get();

        assertThat(parent).isNotNull();
        assertThat(parent.getGrandParent()).isNotNull();
        assertThat(parent.getChildren()).isNull();
    }

    @Test
    @DisplayName("DocumentGenerator with GenerateType.ALL generates both parents and children")
    void documentGenerator_withAllType_generatesBothDirections() {
        DocumentGenerator<Parent> generator = generatorFactory.getGenerator(Parent.class);

        Parent parent = generator
                .generateType(GenerateType.ALL)
                .odmType(ODMType.MONGODB)
                .get();

        assertThat(parent).isNotNull();
        assertThat(parent.getChildren()).isNotNull().isNotEmpty();
        assertThat(parent.getGrandParent()).isNotNull().isInstanceOf(GrandParent.class);
    }

    @Test
    @DisplayName("DocumentGenerator with GenerateType.SELF generates only the document itself")
    void documentGenerator_withSelfType_generatesOnlySelf() {
        DocumentGenerator<Parent> generator = generatorFactory.getGenerator(Parent.class);

        Parent parent = generator
                .generateType(GenerateType.SELF)
                .odmType(ODMType.MONGODB)
                .get();

        assertThat(parent).isNotNull();
        assertThat(parent.getChildren()).isNull();
        assertThat(parent.getGrandParent()).isNull();
    }

    @Test
    @DisplayName("DocumentGenerator for Child class generates a valid instance")
    void documentGenerator_forChildClass_generatesInstance() {
        DocumentGenerator<Child> generator = generatorFactory.getGenerator(Child.class);

        Child child = generator.get();

        assertThat(child).isNotNull();
    }
}
