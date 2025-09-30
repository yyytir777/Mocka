package jodag.mybatisTest;

import jodag.JodagTestApplication;
import jodag.generator.EntityGenerator;
import jodag.generator.GenerateType;
import jodag.generator.SpringGeneratorFactory;
import jodag.mybatis.associations.Child;
import jodag.mybatis.associations.GrandParent;
import jodag.mybatis.associations.Parent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DisplayName("MyBatis GenerateType.PARENT Test Code")
@SpringBootTest(classes = JodagTestApplication.class)
public class GenerateTypeParentTest {

    @Autowired
    SpringGeneratorFactory springGeneratorFactory;

    @Test
    @DisplayName("GenerateType.PARENT generates parent but not child")
    void generate_parent_entity() {
        EntityGenerator<Child> generator = springGeneratorFactory.getGenerator(Child.class);
        Child child = generator.get(GenerateType.PARENT);
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

