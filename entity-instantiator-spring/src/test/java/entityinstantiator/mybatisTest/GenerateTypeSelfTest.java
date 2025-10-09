package entityinstantiator.mybatisTest;

import entityinstantiator.EntityInstantiatorSpringTestApplication;
import entityinstantiator.generator.EntityGenerator;
import entityinstantiator.generator.GenerateType;
import entityinstantiator.generator.SpringGeneratorFactory;
import entityinstantiator.entity.mybatis.associations.Child;
import entityinstantiator.entity.mybatis.associations.GrandParent;
import entityinstantiator.entity.mybatis.associations.Parent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DisplayName("MyBatis GenerateType.SELF Test Code")
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
}


