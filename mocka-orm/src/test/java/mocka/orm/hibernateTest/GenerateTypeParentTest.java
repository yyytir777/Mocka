package mocka.orm.hibernateTest;

import mocka.orm.MockaSpringTestApplication;
import mocka.orm.generator.EntityGenerator;
import mocka.core.GenerateType;
import mocka.orm.entity.orm.associations.Child;
import mocka.orm.entity.orm.associations.Parent;
import mocka.orm.generator.EntityGeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DisplayName("Hibernate GenerateType.PARENT Test Code")
@SpringBootTest(classes = MockaSpringTestApplication.class)
public class GenerateTypeParentTest {

    @Autowired
    EntityGeneratorFactory generatorFactory;

    @Test
    @DisplayName("GenerateType.PARENT generates parent but not child")
    void generate_parent_entity() {
        EntityGenerator<Child> generator = generatorFactory.getGenerator(Child.class);
        Child child = generator.generateType(GenerateType.PARENT).get();
        System.out.println("child = " + child);
        assertThat(child).isNotNull().isInstanceOf(Child.class);

        Parent parent = child.getParent();
        System.out.println("parent = " + parent);
        assertThat(parent).isNotNull().isInstanceOf(Parent.class);
        assertThat(parent.getChildren()).isNotNull().isNotEmpty();
        assertThat(parent.getGrandParent()).isNull();
    }
}

