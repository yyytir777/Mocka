package mocka.odm.fileSourceTest;

import mocka.odm.MockaOdmTestApplication;
import mocka.odm.document.filesource.yaml.*;
import mocka.odm.generator.DocumentGenerator;
import mocka.odm.generator.DocumentGeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@DisplayName("FileSource YAML Test Code")
@SpringBootTest(classes = MockaOdmTestApplication.class)
public class FileSourceYamlTest {

    @Autowired
    DocumentGeneratorFactory generatorFactory;

    @Test
    @DisplayName("generates a document by loading data from a YAML file")
    public void create_document_by_loading_data_from_yaml() {
        DocumentGenerator<FileSourceYamlDocument> generator = generatorFactory.getGenerator(FileSourceYamlDocument.class);
        FileSourceYamlDocument document = generator.get();
        System.out.println("fileSourceYamlDocument = " + document);
        assertThat(document).isNotNull().isInstanceOf(FileSourceYamlDocument.class);
    }

    @Test
    @DisplayName("supports variant type fields in file_source_all_type.yaml")
    public void supports_variant_type_fields_in_file_source_all_type() {
        DocumentGenerator<FileSourceYamlAllTypeDocument> generator = generatorFactory.getGenerator(FileSourceYamlAllTypeDocument.class);
        FileSourceYamlAllTypeDocument document = generator.get();
        System.out.println("fileSourceYamlAllTypeDocument = " + document);
        assertThat(document).isNotNull().isInstanceOf(FileSourceYamlAllTypeDocument.class);
    }

    @Test
    @DisplayName("throws RuntimeException when creating a document by loading data from empty YAML file")
    public void throw_exception_when_loading_data_from_empty_yaml() {
        DocumentGenerator<FileSourceYamlEmptyFailDocument> generator = generatorFactory.getGenerator(FileSourceYamlEmptyFailDocument.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("throws RuntimeException when creating a document by loading data from invalid grammar YAML file")
    public void throw_exception_when_loading_data_from_invalid_grammar_yaml() {
        DocumentGenerator<FileSourceYamlInvalidGrammarFailDocument> generator = generatorFactory.getGenerator(FileSourceYamlInvalidGrammarFailDocument.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("throws RuntimeException when creating a document by loading data from invalid path YAML file")
    public void throw_exception_when_loading_data_from_invalid_path_yaml() {
        DocumentGenerator<FileSourceYamlInvalidPathFailDocument> generator = generatorFactory.getGenerator(FileSourceYamlInvalidPathFailDocument.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(RuntimeException.class);
    }
}
