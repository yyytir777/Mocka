package mocka.orm.annotationTest;

import mocka.orm.annotation.mocka.Mocka;
import mocka.orm.annotation.mocka.MockaConfig;
import mocka.orm.annotation.mocka.MockaExtension;
import mocka.orm.entity.orm.associations.Child;
import mocka.orm.entity.orm.associations.Parent;
import mocka.core.GenerateType;
import mocka.orm.generator.ORMType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockaExtension.class)
@MockaConfig(size = 10, ormType = ORMType.HIBERNATE)
@DisplayName("MockaConfig Annotation Test Code")
public class MockaConfigAnnotationTest {

    @Mocka(GenerateType.CHILD)
    Parent parent;

    @Test
    @DisplayName("generate_children_with_size_specified_in_mockaConfig_annotation")
    void generate_children_with_size_specified_in_mocka_config() {
        List<Child> children = parent.getChildren();
        System.out.println("children = " + children);
        System.out.println("parent.getTest() = " + parent.getTest());
        assertThat(children.size()).isEqualTo(10);
    }
}
