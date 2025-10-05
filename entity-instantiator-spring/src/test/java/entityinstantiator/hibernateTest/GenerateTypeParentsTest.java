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

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DisplayName("Hibernate GenerateType.PARENTS Test Code")
@SpringBootTest(classes = EntityInstantiatorSpringTestApplication.class)
public class GenerateTypeParentsTest {

    @Autowired
    SpringGeneratorFactory springGeneratorFactory;

    @Test
    @DisplayName("GenerateType.PARENTS generates parent and grandParent entity (follows parent association)")
    void generate_parent_entity() {
        EntityGenerator<Child> generator = springGeneratorFactory.getGenerator(Child.class);
        Child child = generator.get(GenerateType.PARENTS);
        System.out.println("child = " + child);
        assertThat(child).isNotNull().isInstanceOf(Child.class);

        Parent parent = child.getParent();
        System.out.println("parent = " + parent);
        assertThat(parent).isNotNull().isInstanceOf(Parent.class);
        assertThat(parent.getChildren()).isNotNull().isNotEmpty();
        assertThat(parent.getGrandParent()).isNotNull().isInstanceOf(GrandParent.class);

        GrandParent grandParent = parent.getGrandParent();
        System.out.println("grandParent = " + grandParent);
        assertThat(grandParent).isNotNull().isInstanceOf(GrandParent.class);
        assertThat(grandParent.getParents()).isNotNull().isNotEmpty();
    }
}

