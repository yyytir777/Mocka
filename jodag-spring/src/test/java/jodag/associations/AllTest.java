package jodag.associations;

import jodag.TestConfig;
import jodag.entity.associations.Child;
import jodag.entity.associations.GrandParent;
import jodag.entity.associations.Parent;
import jodag.generator.EntityGenerator;
import jodag.generator.GenerateType;
import jodag.generator.SpringGeneratorFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Disabled
@DisplayName("모든 연관관계 테스트")
@SpringBootTest(classes = TestConfig.class)
public class AllTest {

    @Test
    @DisplayName("연관관계에 관계 없이 모든 연관관계를 맺은 엔티티를 생성 -> GenerateType.ALL")
    void generate_all_entity() {
        EntityGenerator<Parent> generator = SpringGeneratorFactory.getGenerator(Parent.class);

        Parent parent = generator.get(GenerateType.ALL);

        List<Child> children = parent.getChildren();
        GrandParent grandParent = parent.getGrandParent();

        children.forEach(child -> assertThat(child).isInstanceOf(Child.class));
        assertThat(grandParent).isInstanceOf(GrandParent.class);
    }
}
