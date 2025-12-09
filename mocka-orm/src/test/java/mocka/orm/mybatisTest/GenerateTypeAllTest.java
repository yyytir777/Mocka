package mocka.orm.mybatisTest;

import mocka.orm.MockaSpringTestApplication;
import mocka.orm.entity.orm.associations.Child;
import mocka.orm.entity.orm.associations.GrandParent;
import mocka.orm.entity.orm.associations.Parent;
import mocka.orm.generator.EntityGenerator;
import mocka.core.GenerateType;
import mocka.orm.generator.EntityGeneratorFactory;
import mocka.orm.generator.ORMType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DisplayName("MyBatis GenerateType.ALL Test Code")
@SpringBootTest(classes = MockaSpringTestApplication.class)
public class GenerateTypeAllTest {

    @Autowired
    EntityGeneratorFactory generatorFactory;

    @Test
    @DisplayName("GenerateType.ALL option generates all entity with associations")
    void generate_all_entity() {
        EntityGenerator<Parent> generator = generatorFactory.getGenerator(Parent.class);

        Parent parent = generator
                .ormType(ORMType.MYBATIS)
                .generateType(GenerateType.ALL)
                .get();

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

