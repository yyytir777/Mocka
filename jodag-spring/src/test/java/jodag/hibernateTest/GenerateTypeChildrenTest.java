package jodag.hibernateTest;

import jodag.JodagTestApplication;
import jodag.generator.EntityGenerator;
import jodag.generator.GenerateType;
import jodag.generator.SpringGeneratorFactory;
import jodag.hibernate.associations.Child;
import jodag.hibernate.associations.GrandParent;
import jodag.hibernate.associations.Parent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DisplayName("Hibernate GenerateType.CHILDREN Test Code")
@SpringBootTest(classes = JodagTestApplication.class)
public class GenerateTypeChildrenTest {

    @Autowired
    SpringGeneratorFactory springGeneratorFactory;

    @Test
    @DisplayName("GenerateType.CHILDREN generates parent and children entity (follows child association)")
    void generate_children_entity() {
        EntityGenerator<GrandParent> generator = springGeneratorFactory.getGenerator(GrandParent.class);
        GrandParent grandParent = generator.get(GenerateType.CHILDREN);
        assertThat(grandParent).isNotNull().isInstanceOf(GrandParent.class);

        List<Parent> parents = grandParent.getParents();
        assertThat(parents).isNotNull().isNotEmpty();
        parents.forEach(parent -> {
            assertThat(parent).isNotNull().isInstanceOf(Parent.class);
            assertThat(parent.getGrandParent()).isNotNull().isInstanceOf(GrandParent.class);
            assertThat(parent.getChildren()).isNotNull().isNotEmpty();
        });

        List<Child> children = parents.stream()
                .flatMap(parent -> parent.getChildren().stream())
                .toList();
        assertThat(children).isNotNull().isNotEmpty();
        children.forEach(child -> {
            assertThat(child).isNotNull().isInstanceOf(Child.class);
            assertThat(child.getParent()).isNotNull().isInstanceOf(Parent.class);
        });
    }
}

