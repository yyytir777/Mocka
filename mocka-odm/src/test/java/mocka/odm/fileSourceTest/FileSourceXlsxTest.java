package mocka.odm.fileSourceTest;

import mocka.odm.MockaOdmTestApplication;
import mocka.odm.document.filesource.xlsx.*;
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
@DisplayName("FileSource Xlsx Test Code")
@SpringBootTest(classes = MockaOdmTestApplication.class)
public class FileSourceXlsxTest {

    @Autowired
    DocumentGeneratorFactory generatorFactory;

    @Test
    @DisplayName("generates a document by loading data from an Xlsx file")
    public void create_document_by_loading_data_from_xlsx() {
        DocumentGenerator<FileSourceXlsxDocument> generator = generatorFactory.getGenerator(FileSourceXlsxDocument.class);
        FileSourceXlsxDocument document = generator.get();
        System.out.println("fileSourceXlsxDocument = " + document);
        assertThat(document).isNotNull().isInstanceOf(FileSourceXlsxDocument.class);
    }

    @Test
    @DisplayName("supports variant type fields in file_source_all_type.xlsx")
    public void supports_variant_type_fields_in_file_source_all_type() {
        DocumentGenerator<FileSourceXlsxAllTypeDocument> generator = generatorFactory.getGenerator(FileSourceXlsxAllTypeDocument.class);
        FileSourceXlsxAllTypeDocument document = generator.get();
        System.out.println("fileSourceXlsxAllTypeDocument = " + document);
        assertThat(document).isNotNull().isInstanceOf(FileSourceXlsxAllTypeDocument.class);
    }

    @Test
    @DisplayName("throws RuntimeException when creating a document by loading data from empty Xlsx file")
    public void throw_exception_when_loading_data_from_empty_xlsx() {
        DocumentGenerator<FileSourceXlsxEmptyFailDocument> generator = generatorFactory.getGenerator(FileSourceXlsxEmptyFailDocument.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(RuntimeException.class);
    }
}
