package mocka.annotationTest;

import mocka.annotation.mocka.Mocka;
import mocka.annotation.mocka.MockaExtension;
import mocka.entity.hibernate.associations.Parent;
import mocka.generator.GenerateType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockaExtension.class)
@DisplayName("Mocka Annotation Test")
public class MockaAnnotationTest {

    @Mocka(GenerateType.ALL)
    Parent all_parent;

    @Mocka(GenerateType.CHILD)
    Parent child_parent;

    @Mocka(GenerateType.CHILDREN)
    Parent children_parent;

    @Mocka(GenerateType.PARENT)
    Parent parent_parent;

    @Mocka(GenerateType.PARENTS)
    Parent parents_parent;

    @Test
    void test_mocka_annotation_test_with_GenerateTypeAll() {
        System.out.println("all_parent = " + all_parent);
        assertThat(all_parent).isNotNull();
        assertThat(all_parent.getGrandParent()).isNotNull();
        assertThat(all_parent.getChildren()).isNotEmpty();
    }

    @Test
    void test_mocka_annotation_test_with_GenerateTypeChild() {
        System.out.println("child_parent = " + child_parent);
        assertThat(child_parent).isNotNull();
        assertThat(child_parent.getGrandParent()).isNull();
        assertThat(child_parent.getChildren()).isNotEmpty();
    }

    @Test
    void test_mocka_annotation_test_with_GenerateTypeChildren() {
        System.out.println("children_parent = " + children_parent);
        assertThat(children_parent).isNotNull();
        assertThat(children_parent.getGrandParent()).isNull();
        assertThat(children_parent.getChildren()).isNotEmpty();
    }

    @Test
    void test_mocka_annotation_test_with_GenerateTypeParent() {
        System.out.println("parent_parent = " + parent_parent);
        assertThat(parent_parent).isNotNull();
        assertThat(parent_parent.getGrandParent()).isNotNull();
        assertThat(parent_parent.getChildren()).isNull();
    }

    @Test
    void test_mocka_annotation_test_with_GenerateTypeParents() {
        System.out.println("parents_parent = " + parents_parent);
        assertThat(parents_parent).isNotNull();
        assertThat(parents_parent.getGrandParent()).isNotNull();
        assertThat(parents_parent.getChildren()).isNull();
    }
}
