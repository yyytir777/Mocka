package mocka.odm;

import mocka.odm.document.Member;
import mocka.odm.generator.DocumentGenerator;
import mocka.odm.generator.DocumentGeneratorFactory;
import mocka.odm.generator.ODMResolver;
import mocka.odm.generator.odm.ODMCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = MockaOdmTestApplication.class)
@DisplayName("mocka-odm Main Test Code")
public class MockaOdmMainTest {

    @Autowired
    private DocumentGeneratorInitializer initializer;

    @Autowired
    private DocumentGeneratorFactory generatorFactory;

    @Test
    @DisplayName("Set ODM scan range by the value of 'mocka.odm.odmType in application.yaml. If no settings, use bean definition.")
    void get_odmType() {
        ODMResolver odmResolver = initializer.getOdmSelector();
        List<ODMCreator> creators = odmResolver.getCreators();

        for (ODMCreator odmCreator : creators) {
            System.out.println("odmCreator = " + odmCreator);
            assertThat(odmCreator).isNotNull();
        }
    }

    @Test
    @DisplayName("Verify Document instance generation from Document.class generator")
    void create_document_instance() {
        DocumentGenerator<Member> generator = generatorFactory.getGenerator(Member.class);

        Member member = generator.get();
        assertThat(member.getId()).isNotNull();
        assertThat(member.getName()).isNotNull();

        List<Class<?>> generatorNames = generatorFactory.getDocumentGeneratorNames();
        assertThat(Member.class).isIn(generatorNames);
    }
}
