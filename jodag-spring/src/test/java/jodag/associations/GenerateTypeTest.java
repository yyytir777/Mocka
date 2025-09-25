package jodag.associations;

import jodag.JodagTestApplication;
import jodag.entity.associations.Child;
import jodag.entity.associations.GrandParent;
import jodag.entity.associations.Parent;
import jodag.entity.associations.Tmp;
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

@DisplayName("GenerateType별 테스트 코드")
@ActiveProfiles("test")
@SpringBootTest(classes = JodagTestApplication.class)
public class GenerateTypeTest {

    @Autowired
    SpringGeneratorFactory springGeneratorFactory;

    @Test
    @DisplayName("GenerateType.ALL은 모든 엔티티를 생성합니다.")
    void generateAll() {
        EntityGenerator<Parent> generator = springGeneratorFactory.getGenerator(Parent.class);
        Parent parent = generator.get(GenerateType.ALL);
        List<Child> children = parent.getChildren();
        GrandParent grandParent = parent.getGrandParent();
        List<Tmp> tmps = grandParent.getTmps();

        assertThat(parent).isNotNull().isInstanceOf(Parent.class);
        children.forEach(child -> assertThat(child).isNotNull().isInstanceOf(Child.class));
        assertThat(grandParent).isNotNull().isInstanceOf(GrandParent.class);
        tmps.forEach(tmp -> assertThat(tmp).isNotNull().isInstanceOf(Tmp.class));
    }

    @Test
    @DisplayName("GenerateType.SELF는 자신의 엔티티 하나만 생성합니다.")
    void generateSelf() {
        EntityGenerator<Parent> generator = springGeneratorFactory.getGenerator(Parent.class);
        Parent parent = generator.get(GenerateType.SELF);
        List<Child> children = parent.getChildren();
        GrandParent grandParent = parent.getGrandParent();

        assertThat(parent).isNotNull().isInstanceOf(Parent.class);
        assertThat(grandParent).isNull();
        assertThat(children).isNull();
    }

    @Test
    @DisplayName("GenerateType.CHILD은 자식 방향의 엔티티를 생성합니다.")
    void generateChild() {
        EntityGenerator<Parent> generator = springGeneratorFactory.getGenerator(Parent.class);
        Parent parent = generator.get(GenerateType.CHILD);

        List<Child> children = parent.getChildren();
        GrandParent grandParent = parent.getGrandParent();

        assertThat(children).isNotNull();
        children.forEach(child -> assertThat(child).isNotNull().isInstanceOf(Child.class));
        assertThat(grandParent).isNull();
    }

    @Test
    @DisplayName("GenerateType.CHILDREN은 자식 방향의 모든 엔티티를 생성합니다.")
    void generateChildren() {
        EntityGenerator<GrandParent> generator = springGeneratorFactory.getGenerator(GrandParent.class);
        GrandParent grandParent = generator.get(GenerateType.CHILDREN);

        List<Parent> parents = grandParent.getParents();
        List<Child> children = parents.stream()
                .flatMap(parent -> parent.getChildren().stream())
                .toList();

        assertThat(parents).isNotNull();
        assertThat(children).isNotNull();
        children.forEach(child -> assertThat(child).isNotNull().isInstanceOf(Child.class));
    }

    @Test
    @DisplayName("GenerateType.PARENT은 부모 방향의 엔티티만 생성합니다.")
    void generateParent() {
        EntityGenerator<Child> generator = springGeneratorFactory.getGenerator(Child.class);
        Child child = generator.get(GenerateType.PARENT);

        Parent parent = child.getParent();
        assertThat(child).isNotNull().isInstanceOf(Child.class);
        assertThat(parent).isNotNull().isInstanceOf(Parent.class);
    }

    @Test
    @DisplayName("GenerateType.PARENTS은 부모 방향의 모든 엔티티를 생성합니다.")
    void generateParents() {
        EntityGenerator<Child> generator = springGeneratorFactory.getGenerator(Child.class);
        Child child = generator.get(GenerateType.PARENTS);

        Parent parent = child.getParent();
        GrandParent grandParent = parent.getGrandParent();

        assertThat(child).isNotNull().isInstanceOf(Child.class);
        assertThat(parent).isNotNull().isInstanceOf(Parent.class);
        assertThat(grandParent).isNotNull().isInstanceOf(GrandParent.class);
    }
}
