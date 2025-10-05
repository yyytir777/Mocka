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
@DisplayName("Hibernate GenerateType.CHILD Test Code")
@SpringBootTest(classes = EntityInstantiatorSpringTestApplication.class)
public class GenerateTypeChildTest {

    @Autowired
    SpringGeneratorFactory springGeneratorFactory;

    @Test
    @DisplayName("GenerateType.CHILD generates child but not grandparent")
    void generate_child_entity() {
        EntityGenerator<Parent> generator = springGeneratorFactory.getGenerator(Parent.class);

        Parent parent = generator.get(GenerateType.CHILD);
        System.out.println("parent = " + parent);
        assertThat(parent).isNotNull().isInstanceOf(Parent.class);

        List<Child> children = parent.getChildren();
        System.out.println("children = " + children);
        assertThat(children).isNotNull();
        children.forEach(child -> {
            assertThat(child).isNotNull().isInstanceOf(Child.class);
            assertThat(child.getParent()).isNotNull().isInstanceOf(Parent.class);
        });

        GrandParent grandParent = parent.getGrandParent();
        System.out.println("grandParent = " + grandParent);
        assertThat(grandParent).isNull();
    }
}

