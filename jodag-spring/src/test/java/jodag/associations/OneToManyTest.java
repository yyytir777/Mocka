package jodag.associations;

import jodag.TestConfig;
import jodag.entity.associations.Child;
import jodag.entity.associations.GrandParent;
import jodag.entity.associations.Parent;
import jodag.generator.Generator;
import jodag.generator.SpringGeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("@OneToMany 연관관계 테스트")
@SpringBootTest(classes = TestConfig.class)
public class OneToManyTest {

    @Test
    @DisplayName("엔티티 생성 시 연관관계 생성 X -> GenerateType.SELF")
    void generate_self() {
        Generator<Parent> generator = SpringGeneratorFactory.getGenerator(Parent.class);

        Parent parent = generator.get(GenerateType.SELF);
        assertThat(parent).isNotNull();
        assertThat(parent.getChildren()).isEmpty();
    }

    @Test
    @DisplayName("부모 엔티티 랜덤 생성 시 자식 엔티티도 생성됨 -> GenerateType.CHILD")
    void generate_child() {
        Generator<Parent> generator = SpringGeneratorFactory.getGenerator(Parent.class);

        Parent parent = generator.get(GenerateType.CHILD);
        assertThat(parent).isInstanceOf(Parent.class);

        List<Child> children = parent.getChildren();
        children.forEach(child -> assertThat(child).isInstanceOf(Child.class));
    }

    @Test
    @DisplayName("조부모 엔티티 생성 시 부모 엔티티, 자식 엔티티까지 생성됨 (자식 엔티티 방향으로 생성됨) -> GenerateType.CHILDREN")
    void generate_children() {
        Generator<GrandParent> generator = SpringGeneratorFactory.getGenerator(GrandParent.class);

        GrandParent grandParent = generator.get(GenerateType.CHILDREN);
        assertThat(grandParent).isInstanceOf(GrandParent.class);

        List<Parent> parents = grandParent.getParents();
        parents.forEach(parent -> assertThat(parent).isInstanceOf(Parent.class));

        Parent parent = parents.get(0);
        List<Child> children = parent.getChildren();
        children.forEach(child -> assertThat(child).isInstanceOf(Child.class));
    }
}
