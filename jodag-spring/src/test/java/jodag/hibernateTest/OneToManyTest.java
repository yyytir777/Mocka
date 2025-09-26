package jodag.hibernateTest;

import jodag.JodagTestApplication;
import jodag.hibernate.associations.Child;
import jodag.hibernate.associations.GrandParent;
import jodag.hibernate.associations.Parent;
import jodag.generator.EntityGenerator;
import jodag.generator.GenerateType;
import jodag.generator.SpringGeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("@OneToMany 연관관계 테스트")
@ActiveProfiles("test")
@SpringBootTest(classes = JodagTestApplication.class)
public class OneToManyTest {

    @Autowired
    SpringGeneratorFactory springGeneratorFactory;
    @Test
    @DisplayName("엔티티 생성 시 연관관계 생성 X -> GenerateType.SELF")
    void generate_self() {
        EntityGenerator<Parent> generator = springGeneratorFactory.getGenerator(Parent.class);

        Parent parent = generator.get(GenerateType.SELF);
        assertThat(parent).isNotNull();
        assertThat(parent.getChildren()).isNull();

        System.out.println("parent = " + parent);
    }

    @Test
    @DisplayName("부모 엔티티 랜덤 생성 시 자식 엔티티도 생성됨 -> GenerateType.CHILD")
    void generate_child() {
        EntityGenerator<Parent> generator = springGeneratorFactory.getGenerator(Parent.class);

        Parent parent = generator.get(GenerateType.CHILD);
        assertThat(parent.getChildren()).isNotNull();

        List<Child> children = parent.getChildren();
        children.forEach(child -> {
            assertThat(child).isInstanceOf(Child.class);
            assertThat(child.getParent()).isNotNull();
        });
        assertThat(parent.getChildren().size()).isEqualTo(5);

        System.out.println("parent = " + parent);
        System.out.println("children = " + children);
    }

    @Test
    @DisplayName("조부모 엔티티 생성 시 부모 엔티티, 자식 엔티티까지 생성됨 (자식 엔티티 방향으로 생성됨) -> GenerateType.CHILDREN")
    void generate_children() {
        EntityGenerator<GrandParent> generator = springGeneratorFactory.getGenerator(GrandParent.class);

        GrandParent grandParent = generator.get(GenerateType.CHILDREN);
        assertThat(grandParent).isInstanceOf(GrandParent.class);
        assertThat(grandParent.getParents().size()).isEqualTo(5);


        List<Parent> parents = grandParent.getParents();
        parents.forEach(parent -> {
            assertThat(parent).isInstanceOf(Parent.class);
            assertThat(parent.getChildren().size()).isEqualTo(5);
            assertThat(parent.getGrandParent()).isNotNull();
        });

        List<Child> children = parents.stream()
                .flatMap(parent -> parent.getChildren().stream())
                .peek(child -> {
                    assertThat(child).isInstanceOf(Child.class);
                    assertThat(child.getParent()).isNotNull();
                })
                .toList();

        System.out.println("grandParent = " + grandParent);
        System.out.println("parents = " + parents);
        System.out.println("children = " + children);
    }
}
