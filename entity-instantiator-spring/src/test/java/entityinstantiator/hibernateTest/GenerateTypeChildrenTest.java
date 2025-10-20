package entityinstantiator.hibernateTest;

import entityinstantiator.EntityInstantiatorSpringTestApplication;
import entityinstantiator.generator.EntityGenerator;
import entityinstantiator.generator.GenerateType;
import entityinstantiator.generator.SpringGeneratorFactory;
import entityinstantiator.entity.hibernate.associations.Child;
import entityinstantiator.entity.hibernate.associations.GrandParent;
import entityinstantiator.entity.hibernate.associations.Parent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DisplayName("Hibernate GenerateType.CHILDREN Test Code")
@SpringBootTest(classes = EntityInstantiatorSpringTestApplication.class)
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

        System.out.println("children = " + children);
        System.out.println("parents = " + parents);
        System.out.println("grandParent = " + grandParent);
    }
}

