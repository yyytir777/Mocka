package jodag;

import jodag.hibernate.associations.Parent;
import jodag.generator.EntityGenerator;
import jodag.generator.SpringGeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test")
@SpringBootTest(classes = JodagTestApplication.class)
@DisplayName("jodag-spring vol2 test code")
public class SpringGeneratorFactoryV2Test {

    @Autowired
    SpringGeneratorFactory springGeneratorFactory;

    @Test
    public void test() {
        EntityGenerator<Parent> generator = springGeneratorFactory.getGenerator(Parent.class);
        Parent parent = generator.get();
        System.out.println("parent = " + parent);
    }
}
