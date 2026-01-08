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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DisplayName("MyBatis GenerateType.SELF Test Code")
@SpringBootTest(classes = MockaSpringTestApplication.class)
public class GenerateTypeSelfTest {

    @Autowired
    EntityGeneratorFactory generatorFactory;

    @Test
    @DisplayName("GenerateType.SELF option generates only one entity.")
    void generate_self_entity() {
        EntityGenerator<Parent> generator = generatorFactory.getGenerator(Parent.class);

        Parent parent = generator
                .ormType(ORMType.MYBATIS)
                .generateType(GenerateType.SELF)
                .get();
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
        EntityGenerator<Parent> generator = generatorFactory.getGenerator(Parent.class);

        Parent parent = generator
                .ormType(ORMType.MYBATIS)
                .generateType(GenerateType.SELF)
                .get();
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


