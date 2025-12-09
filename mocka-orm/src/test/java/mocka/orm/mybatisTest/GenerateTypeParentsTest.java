package mocka.orm.mybatisTest;

import mocka.orm.MockaSpringTestApplication;
import mocka.orm.generator.EntityGenerator;
import mocka.core.GenerateType;
import mocka.orm.entity.orm.associations.Child;
import mocka.orm.entity.orm.associations.GrandParent;
import mocka.orm.entity.orm.associations.Parent;
import mocka.orm.generator.EntityGeneratorFactory;
import mocka.orm.generator.ORMType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DisplayName("MyBatis GenerateType.PARENTS Test Code")
@SpringBootTest(classes = MockaSpringTestApplication.class)
public class GenerateTypeParentsTest {

    @Autowired
    EntityGeneratorFactory generatorFactory;

    @Test
    @DisplayName("GenerateType.PARENTS generates parent and grandParent entity (follows parent association)")
    void generate_parent_entity() {
        EntityGenerator<Child> generator = generatorFactory.getGenerator(Child.class);
        Child child = generator
                .ormType(ORMType.MYBATIS)
                .generateType(GenerateType.PARENTS)
                .get();
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

