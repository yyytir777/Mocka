package mocka.odm.fileSourceTest;

import mocka.odm.MockaOdmTestApplication;
import mocka.odm.document.filesource.xml.*;
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
@DisplayName("FileSource XML Test Code")
@SpringBootTest(classes = MockaOdmTestApplication.class)
public class FileSourceXmlTest {

    @Autowired
    DocumentGeneratorFactory generatorFactory;

    @Test
    @DisplayName("generates a document by loading data from an XML file")
    public void create_document_by_loading_data_from_xml() {
        DocumentGenerator<FileSourceXmlDocument> generator = generatorFactory.getGenerator(FileSourceXmlDocument.class);
        FileSourceXmlDocument document = generator.get();
        assertThat(document).isNotNull().isInstanceOf(FileSourceXmlDocument.class);
    }

    @Test
    @DisplayName("supports variant type fields in file_source_all_type.xml")
    public void supports_variant_type_fields_in_file_source_all_type() {
        DocumentGenerator<FileSourceXmlAllTypeDocument> generator = generatorFactory.getGenerator(FileSourceXmlAllTypeDocument.class);
        FileSourceXmlAllTypeDocument document = generator.get();
        System.out.println("fileSourceXmlAllTypeDocument = " + document);
        assertThat(document).isNotNull().isInstanceOf(FileSourceXmlAllTypeDocument.class);
    }

    @Test
    @DisplayName("throws RuntimeException when creating a document by loading data from empty XML file")
    public void throw_exception_when_loading_data_from_empty_xml() {
        DocumentGenerator<FileSourceXmlEmptyFailDocument> generator = generatorFactory.getGenerator(FileSourceXmlEmptyFailDocument.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("throws RuntimeException when creating a document by loading data from invalid grammar XML file")
    public void throw_exception_when_loading_data_from_invalid_grammar_xml() {
        DocumentGenerator<FileSourceXmlInvalidGrammarFailDocument> generator = generatorFactory.getGenerator(FileSourceXmlInvalidGrammarFailDocument.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("throws RuntimeException when creating a document by loading data from invalid path XML file")
    public void throw_exception_when_loading_data_from_invalid_path_xml() {
        DocumentGenerator<FileSourceXmlInvalidPathFailDocument> generator = generatorFactory.getGenerator(FileSourceXmlInvalidPathFailDocument.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(RuntimeException.class);
    }
}
