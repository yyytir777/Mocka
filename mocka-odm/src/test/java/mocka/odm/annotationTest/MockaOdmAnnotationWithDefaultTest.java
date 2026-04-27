package mocka.odm.annotationTest;

import mocka.core.annotation.Mocka;
import mocka.odm.MockaOdmTestApplication;
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
@DisplayName("Mocka Annotation With Default(GenerateType.SELF) Test")
public class MockaOdmAnnotationWithDefaultTest {

    @Mocka
    Parent parent;

    @Test
    void test_mocka_annotation_test_with_default() {
        assertThat(parent).isNotNull();
        assertThat(parent.getGrandParent()).isNull();
        assertThat(parent.getChildren()).isNull();
    }
}
