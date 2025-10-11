package entityinstantiator.fileSourceTest;

import entityinstantiator.EntityInstantiatorSpringTestApplication;
import entityinstantiator.entity.filesource.csv.FileSourceCsvEmptyFailEntity;
import entityinstantiator.entity.filesource.csv.FileSourceCsvEntity;
import entityinstantiator.entity.filesource.csv.FileSourceCsvInvalidPathFailEntity;
import entityinstantiator.entity.filesource.xlsx.FileSourceXlsxAllTypeEntity;
import entityinstantiator.entity.filesource.xlsx.FileSourceXlsxEmptyFailEntity;
import entityinstantiator.entity.filesource.xlsx.FileSourceXlsxEntity;
import entityinstantiator.entity.hibernate.Member;
import entityinstantiator.generator.EntityGenerator;
import entityinstantiator.generator.SpringGeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@DisplayName("FileSource Xlsx Test Code")
@SpringBootTest(classes = EntityInstantiatorSpringTestApplication.class)
public class FileSourceXlsxTest {

    @Autowired SpringGeneratorFactory springGeneratorFactory;

    @Test
    @DisplayName("generates an instance by loading data from an Xlsx file")
    public void create_instance_by_loading_data_from_xlsx() {
        EntityGenerator<FileSourceXlsxEntity> generator = springGeneratorFactory.getGenerator(FileSourceXlsxEntity.class);
        FileSourceXlsxEntity fileSourceXlsxEntity = generator.get();
        System.out.println("fileSourceXlsxEntity = " + fileSourceXlsxEntity);
        assertThat(fileSourceXlsxEntity).isNotNull().isInstanceOf(FileSourceXlsxEntity.class);
    }

    @Test
    @DisplayName("supports variant type fields in file_source_all_type.xlsx")
    public void supports_variant_type_fields_in_file_source_all_type() {
        EntityGenerator<FileSourceXlsxAllTypeEntity> generator = springGeneratorFactory.getGenerator(FileSourceXlsxAllTypeEntity.class);
        FileSourceXlsxAllTypeEntity fileSourceXlsxAllTypeEntity = generator.get();
        System.out.println("fileSourceXlsxAllTypeEntity = " + fileSourceXlsxAllTypeEntity);
        assertThat(fileSourceXlsxAllTypeEntity).isNotNull().isInstanceOf(FileSourceXlsxAllTypeEntity.class);
    }

    @Test
    @DisplayName("throws RuntimeException when creating an instance by loading data from empty Xlsx file")
    public void throw_exception_when_loading_data_from_empty_xlsx() {
        EntityGenerator<FileSourceXlsxEmptyFailEntity> generator = springGeneratorFactory.getGenerator(FileSourceXlsxEmptyFailEntity.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(RuntimeException.class);
    }
}
