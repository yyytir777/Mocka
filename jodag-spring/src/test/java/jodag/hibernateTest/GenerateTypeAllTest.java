package jodag.hibernateTest;

import jodag.JodagTestApplication;
import jodag.hibernate.associations.Child;
import jodag.hibernate.associations.GrandParent;
import jodag.hibernate.associations.Parent;
import jodag.generator.EntityGenerator;
import jodag.generator.GenerateType;
import jodag.generator.SpringGeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@DisplayName("Hibernate GenerateType.ALL Test Code")
@SpringBootTest(classes = JodagTestApplication.class)
public class GenerateTypeAllTest {

    @Autowired
    SpringGeneratorFactory springGeneratorFactory;

    @Test
    @DisplayName("GenerateType.ALL option generates all entity with associations")
    void generate_all_entity() {
        EntityGenerator<Parent> generator = springGeneratorFactory.getGenerator(Parent.class);

        Parent parent = generator.get(GenerateType.ALL);
        System.out.println("parent = " + parent);
        assertThat(parent).isNotNull().isInstanceOf(Parent.class);

        List<Child> children = parent.getChildren();
        System.out.println("children = " + children);
        assertThat(children).isNotNull();
        children.forEach(child -> {
            assertThat(child).isNotNull();
            assertThat(child.getParent()).isNotNull();
        });

        GrandParent grandParent = parent.getGrandParent();
        System.out.println("grandParent = " + grandParent);
        assertThat(grandParent).isNotNull().isInstanceOf(GrandParent.class);

        List<Parent> parents = grandParent.getParents();
        parents.forEach(parent1 -> {
            assertThat(parent1).isNotNull();
            assertThat(parent1.getGrandParent()).isNotNull();
            assertThat(parent1.getChildren()).isNotNull();
        });
    }
}

