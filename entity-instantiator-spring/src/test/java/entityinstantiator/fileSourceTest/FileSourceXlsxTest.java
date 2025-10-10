package entityinstantiator.fileSourceTest;

import entityinstantiator.EntityInstantiatorSpringTestApplication;
import entityinstantiator.entity.filesource.csv.FileSourceCsvEmptyFailEntity;
import entityinstantiator.entity.filesource.csv.FileSourceCsvEntity;
import entityinstantiator.entity.filesource.csv.FileSourceCsvInvalidPathFailEntity;
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
    public void create_instance_by_loading_data_from_csv() {
        EntityGenerator<FileSourceXlsxEntity> generator = springGeneratorFactory.getGenerator(FileSourceXlsxEntity.class);
        FileSourceXlsxEntity fileSourceXlsxEntity = generator.get();
        System.out.println("fileSourceXlsxEntity = " + fileSourceXlsxEntity);
        assertThat(fileSourceXlsxEntity).isNotNull().isInstanceOf(FileSourceXlsxEntity.class);
    }

    @Test
    void test() {
        EntityGenerator<Member> generator = springGeneratorFactory.getGenerator(Member.class);
        Member member = generator.get();
        System.out.println("member = " + member);
    }

//    @Test
//    @DisplayName("throws RuntimeException when creating an instance by loading data from empty CSV file")
//    public void throw_exception_when_loading_data_from_empty_csv() {
//        EntityGenerator<FileSourceCsvEmptyFailEntity> generator = springGeneratorFactory.getGenerator(FileSourceCsvEmptyFailEntity.class);
//        assertThatThrownBy(generator::get)
//                .isInstanceOf(RuntimeException.class);
//    }
//
//    @Test
//    @DisplayName("throws RuntimeException when creating an instance by loading data from invalid path CSV file")
//    public void throw_exception_when_loading_data_from_invalid_path_csv() {
//        EntityGenerator<FileSourceCsvInvalidPathFailEntity> generator = springGeneratorFactory.getGenerator(FileSourceCsvInvalidPathFailEntity.class);
//        assertThatThrownBy(generator::get)
//                .isInstanceOf(RuntimeException.class);
//    }
}
