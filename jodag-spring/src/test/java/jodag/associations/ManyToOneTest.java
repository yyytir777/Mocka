package jodag.associations;

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

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("@ManyToOne 연관관계 테스트")
@ActiveProfiles("test")
@SpringBootTest(classes = JodagTestApplication.class)
public class ManyToOneTest {

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
    @DisplayName("자식 엔티티 랜덤 생성 시 부모 엔티티도 생성됨 -> GenerateType.PARENT")
    void generate_parent() {
        EntityGenerator<Child> generator = springGeneratorFactory.getGenerator(Child.class);

        Child child = generator.get(GenerateType.PARENT);
        assertThat(child).isInstanceOf(Child.class);

        Parent parent = child.getParent();
        assertThat(parent).isInstanceOf(Parent.class);
        assertThat(parent.getChildren()).isNotNull();

        System.out.println("child = " + child);
        System.out.println("parent = " + parent);
    }

    @Test
    @DisplayName("자식 엔티티 랜덤 생성 시 부모 엔티티 방향으로 모두 생성됨 -> GenerateType.PARENTS")
    void generate_parents() {
        EntityGenerator<Child> generator = springGeneratorFactory.getGenerator(Child.class);

        Child child = generator.get(GenerateType.PARENTS);
        assertThat(child).isInstanceOf(Child.class);

        Parent parent = child.getParent();
        assertThat(parent).isInstanceOf(Parent.class);

        GrandParent grandParent = parent.getGrandParent();
        assertThat(grandParent).isInstanceOf(GrandParent.class);

        System.out.println("child = " + child);
        System.out.println("parent = " + parent);
        System.out.println("grandParent = " + grandParent);
    }
}
