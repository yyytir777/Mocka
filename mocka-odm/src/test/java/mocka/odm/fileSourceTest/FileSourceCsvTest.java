package mocka.odm.fileSourceTest;

import mocka.odm.MockaOdmTestApplication;
import mocka.odm.document.filesource.csv.*;
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
@DisplayName("FileSource CSV Test Code")
@SpringBootTest(classes = MockaOdmTestApplication.class)
public class FileSourceCsvTest {

    @Autowired
    DocumentGeneratorFactory generatorFactory;

    @Test
    @DisplayName("generates a document by loading data from a CSV file")
    public void create_document_by_loading_data_from_csv() {
        DocumentGenerator<FileSourceCsvDocument> generator = generatorFactory.getGenerator(FileSourceCsvDocument.class);
        FileSourceCsvDocument document = generator.get();
        System.out.println("fileSourceCsvDocument = " + document);
        assertThat(document).isNotNull().isInstanceOf(FileSourceCsvDocument.class);
    }

    @Test
    @DisplayName("supports variant type fields in file_source_all_type.csv")
    public void supports_variant_type_fields_in_file_source_all_type() {
        DocumentGenerator<FileSourceCsvAllTypeDocument> generator = generatorFactory.getGenerator(FileSourceCsvAllTypeDocument.class);
        FileSourceCsvAllTypeDocument document = generator.get();
        System.out.println("fileSourceCsvAllTypeDocument = " + document);
        assertThat(document).isNotNull().isInstanceOf(FileSourceCsvAllTypeDocument.class);
    }

    @Test
    @DisplayName("throws RuntimeException when creating a document by loading data from empty CSV file")
    public void throw_exception_when_loading_data_from_empty_csv() {
        DocumentGenerator<FileSourceCsvEmptyFailDocument> generator = generatorFactory.getGenerator(FileSourceCsvEmptyFailDocument.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("throws RuntimeException when creating a document by loading data from invalid path CSV file")
    public void throw_exception_when_loading_data_from_invalid_path_csv() {
        DocumentGenerator<FileSourceCsvInvalidPathFailDocument> generator = generatorFactory.getGenerator(FileSourceCsvInvalidPathFailDocument.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(RuntimeException.class);
    }
}
