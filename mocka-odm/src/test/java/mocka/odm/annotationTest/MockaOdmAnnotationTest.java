package mocka.odm.annotationTest;

import mocka.core.GenerateType;
import mocka.core.annotation.Mocka;
import mocka.odm.MockaOdmTestApplication;
import mocka.odm.document.association.GrandParent;
import mocka.odm.document.association.Parent;
import mocka.odm.generator.annotation.mocka.MockaOdmExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(classes = {MockaOdmTestApplication.class})
@ExtendWith(MockaOdmExtension.class)
@DisplayName("Mocka ODM Annotation Test Code")
public class MockaOdmAnnotationTest {

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
    void test_mocka_odm_annotation_with_GenerateTypeAll() {
        System.out.println("all_parent = " + all_parent);
        assertThat(all_parent).isNotNull();
        assertThat(all_parent.getChildren()).isNotEmpty();
        assertThat(all_parent.getGrandParent()).isNotNull().isInstanceOf(GrandParent.class);
    }

    @Test
    void test_mocka_odm_annotation_with_GenerateTypeChild() {
        System.out.println("child_parent = " + child_parent);
        assertThat(child_parent).isNotNull();
        assertThat(child_parent.getGrandParent()).isNull();
        assertThat(child_parent.getChildren()).isNotEmpty();
    }

    @Test
    void test_mocka_odm_annotation_with_GenerateTypeChildren() {
        System.out.println("children_parent = " + children_parent);
        assertThat(children_parent).isNotNull();
        assertThat(children_parent.getGrandParent()).isNull();
        assertThat(children_parent.getChildren()).isNotEmpty();
    }

    @Test
    void test_mocka_odm_annotation_with_GenerateTypeParent() {
        System.out.println("parent_parent = " + parent_parent);
        assertThat(parent_parent).isNotNull();
        assertThat(parent_parent.getGrandParent()).isNotNull();
        assertThat(parent_parent.getChildren()).isNull();
    }

    @Test
    void test_mocka_odm_annotation_with_GenerateTypeParents() {
        System.out.println("parents_parent = " + parents_parent);
        assertThat(parents_parent).isNotNull();
        assertThat(parents_parent.getGrandParent()).isNotNull();
        assertThat(parents_parent.getChildren()).isNull();
    }
}
