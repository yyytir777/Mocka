package mocka.mybatisTest;

import mocka.MockaSpringTestApplication;
import mocka.generator.EntityGenerator;
import mocka.generator.GenerateType;
import mocka.generator.SpringGeneratorFactory;
import mocka.entity.orm.associations.Child;
import mocka.entity.orm.associations.GrandParent;
import mocka.entity.orm.associations.Parent;
import mocka.generator.orm.ORMType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DisplayName("MyBatis GenerateType.PARENT Test Code")
@SpringBootTest(classes = MockaSpringTestApplication.class)
public class GenerateTypeParentTest {

    @Autowired
    SpringGeneratorFactory springGeneratorFactory;

    @Test
    @DisplayName("GenerateType.PARENT generates parent but not child")
    void generate_parent_entity() {
        EntityGenerator<Child> generator = springGeneratorFactory.getGenerator(Child.class);
        Child child = generator.get(ORMType.MYBATIS, GenerateType.PARENT);
        System.out.println("child = " + child);
        assertThat(child).isNotNull().isInstanceOf(Child.class);

        Parent parent = child.getParent();
        System.out.println("parent = " + parent);
        assertThat(parent).isNotNull().isInstanceOf(Parent.class);
        assertThat(parent.getChildren()).isNotNull().isNotEmpty();

        GrandParent grandParent = parent.getGrandParent();
        System.out.println("grandParent = " + grandParent);
        assertThat(grandParent).isNull();
    }
}

