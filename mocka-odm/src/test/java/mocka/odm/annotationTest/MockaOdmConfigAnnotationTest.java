package mocka.odm.annotationTest;

import mocka.core.GenerateType;
import mocka.core.annotation.Mocka;
import mocka.odm.MockaOdmTestApplication;
import mocka.odm.document.association.Child;
import mocka.odm.document.association.Parent;
import mocka.odm.generator.ODMType;
import mocka.odm.generator.annotation.mocka.MockaOdmConfig;
import mocka.odm.generator.annotation.mocka.MockaOdmExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(classes = {MockaOdmTestApplication.class})
@ExtendWith(MockaOdmExtension.class)
@MockaOdmConfig(size = 10, odmType = ODMType.MONGODB)
@DisplayName("MockaOdmConfig Annotation Test Code")
public class MockaOdmConfigAnnotationTest {

    @Mocka(GenerateType.CHILD)
    Parent parent;

    @Test
    @DisplayName("generate children with size specified in MockaOdmConfig annotation")
    void generate_children_with_size_specified_in_mocka_odm_config() {
        List<Child> children = parent.getChildren();
        assertThat(children.size()).isEqualTo(10);
    }
}
