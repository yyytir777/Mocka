package mocka.orm.fileSourceTest;

import mocka.orm.MockaSpringTestApplication;
import mocka.orm.entity.filesource.csv.FileSourceCsvAllTypeEntity;
import mocka.orm.entity.filesource.csv.FileSourceCsvEmptyFailEntity;
import mocka.orm.entity.filesource.csv.FileSourceCsvEntity;
import mocka.orm.entity.filesource.csv.FileSourceCsvInvalidPathFailEntity;
import mocka.orm.generator.EntityGenerator;
import mocka.orm.generator.EntityGeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@DisplayName("FileSource CSV Test Code")
@SpringBootTest(classes = MockaSpringTestApplication.class)
public class FileSourceCsvTest {

    @Autowired
    EntityGeneratorFactory generatorFactory;

    @Test
    @DisplayName("generates an instance by loading data from an CSV file")
    public void create_instance_by_loading_data_from_csv() {
        EntityGenerator<FileSourceCsvEntity> generator = generatorFactory.getGenerator(FileSourceCsvEntity.class);
        FileSourceCsvEntity fileSourceCsvEntity = generator.get();
        System.out.println("fileSourceCsvEntity = " + fileSourceCsvEntity);
        assertThat(fileSourceCsvEntity).isNotNull().isInstanceOf(FileSourceCsvEntity.class);
    }

    @Test
    @DisplayName("supports variant type fields in file_source_all_type.csv")
    public void supports_variant_type_fields_in_file_source_all_type() {
        EntityGenerator<FileSourceCsvAllTypeEntity> generator = generatorFactory.getGenerator(FileSourceCsvAllTypeEntity.class);
        FileSourceCsvAllTypeEntity fileSourceCsvAllTypeEntity = generator.get();
        System.out.println("fileSourceCsvAllTypeEntity = " + fileSourceCsvAllTypeEntity);
        assertThat(fileSourceCsvAllTypeEntity).isNotNull().isInstanceOf(FileSourceCsvAllTypeEntity.class);
    }

    @Test
    @DisplayName("throws RuntimeException when creating an instance by loading data from empty CSV file")
    public void throw_exception_when_loading_data_from_empty_csv() {
        EntityGenerator<FileSourceCsvEmptyFailEntity> generator = generatorFactory.getGenerator(FileSourceCsvEmptyFailEntity.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("throws RuntimeException when creating an instance by loading data from invalid path CSV file")
    public void throw_exception_when_loading_data_from_invalid_path_csv() {
        EntityGenerator<FileSourceCsvInvalidPathFailEntity> generator = generatorFactory.getGenerator(FileSourceCsvInvalidPathFailEntity.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(RuntimeException.class);
    }
}
