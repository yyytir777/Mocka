package mocka.odm.fileSourceTest;

import mocka.odm.MockaOdmTestApplication;
import mocka.odm.document.filesource.json.*;
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
@DisplayName("FileSource JSON Test Code")
@SpringBootTest(classes = MockaOdmTestApplication.class)
public class FileSourceJsonTest {

    @Autowired
    DocumentGeneratorFactory generatorFactory;

    @Test
    @DisplayName("generates a document by loading data from a JSON file")
    public void create_document_by_loading_data_from_json() {
        DocumentGenerator<FileSourceJsonDocument> generator = generatorFactory.getGenerator(FileSourceJsonDocument.class);
        FileSourceJsonDocument document = generator.get();
        assertThat(document).isNotNull().isInstanceOf(FileSourceJsonDocument.class);
    }

    @Test
    @DisplayName("supports variant type fields in file_source_all_type.json")
    public void supports_variant_type_fields_in_file_source_all_type() {
        DocumentGenerator<FileSourceJsonAllTypeDocument> generator = generatorFactory.getGenerator(FileSourceJsonAllTypeDocument.class);
        FileSourceJsonAllTypeDocument document = generator.get();
        System.out.println("fileSourceJsonAllTypeDocument = " + document);
        assertThat(document).isNotNull().isInstanceOf(FileSourceJsonAllTypeDocument.class);
    }

    @Test
    @DisplayName("throws RuntimeException when creating a document by loading data from empty JSON file")
    public void throw_exception_when_loading_data_from_empty_json() {
        DocumentGenerator<FileSourceJsonEmptyFailDocument> generator = generatorFactory.getGenerator(FileSourceJsonEmptyFailDocument.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("throws RuntimeException when creating a document by loading data from invalid grammar JSON file")
    public void throw_exception_when_loading_data_from_invalid_grammar_json() {
        DocumentGenerator<FileSourceJsonInvalidGrammarFailDocument> generator = generatorFactory.getGenerator(FileSourceJsonInvalidGrammarFailDocument.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("throws RuntimeException when creating a document by loading data from invalid path JSON file")
    public void throw_exception_when_loading_data_from_invalid_path_json() {
        DocumentGenerator<FileSourceJsonInvalidPathFailDocument> generator = generatorFactory.getGenerator(FileSourceJsonInvalidPathFailDocument.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(RuntimeException.class);
    }
}
