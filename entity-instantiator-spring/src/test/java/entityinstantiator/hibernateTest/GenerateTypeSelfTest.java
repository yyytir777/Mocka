package entityinstantiator.hibernateTest;

import entityinstantiator.EntityInstantiatorSpringTestApplication;
import entityinstantiator.generator.EntityGenerator;
import entityinstantiator.generator.GenerateType;
import entityinstantiator.generator.SpringGeneratorFactory;
import entityinstantiator.entity.hibernate.associations.Child;
import entityinstantiator.entity.hibernate.associations.GrandParent;
import entityinstantiator.entity.hibernate.associations.Parent;
import entityinstantiator.generator.orm.ORMType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DisplayName("Hibernate GenerateType.SELF Test Code")
@SpringBootTest(classes = EntityInstantiatorSpringTestApplication.class)
public class GenerateTypeSelfTest {

    @Autowired
    SpringGeneratorFactory springGeneratorFactory;

    @Test
    @DisplayName("GenerateType.SELF option generates only one entity.")
    void generate_self_entity() {
        EntityGenerator<Parent> generator = springGeneratorFactory.getGenerator(Parent.class);

        Parent parent = generator.get(GenerateType.SELF);
        System.out.println("parent = " + parent);
        assertThat(parent).isNotNull().isInstanceOf(Parent.class);

        List<Child> children = parent.getChildren();
        System.out.println("children = " + children);
        assertThat(children).isNull();

        GrandParent grandParent = parent.getGrandParent();
        System.out.println("grandParent = " + grandParent);
        assertThat(grandParent).isNull();
    }

    @Test
    @DisplayName("GenerateType.SELF option generates only one entity with ORMType param")
    void generate_self_entity_with_orm_type_param() {
        EntityGenerator<Parent> generator = springGeneratorFactory.getGenerator(Parent.class);

        Parent parent = generator.get(ORMType.HIBERNATE, GenerateType.SELF);
        System.out.println("parent = " + parent);
        assertThat(parent).isNotNull().isInstanceOf(Parent.class);

        List<Child> children = parent.getChildren();
        System.out.println("children = " + children);
        assertThat(children).isNull();

        GrandParent grandParent = parent.getGrandParent();
        System.out.println("grandParent = " + grandParent);
        assertThat(grandParent).isNull();
    }

}


