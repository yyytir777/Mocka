package mocka.orm.fileSourceTest;

import mocka.orm.MockaSpringTestApplication;
import mocka.orm.entity.filesource.json.*;
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
@DisplayName("FileSource JSON Test Code")
@SpringBootTest(classes = MockaSpringTestApplication.class)
public class FileSourceJsonTest {

    @Autowired
    EntityGeneratorFactory generatorFactory;

    @Test
    @DisplayName("generates an instance by loading data from an JSON file")
    public void create_instance_by_loading_data_from_json() {
        EntityGenerator<FileSourceJsonEntity> generator = generatorFactory.getGenerator(FileSourceJsonEntity.class);
        FileSourceJsonEntity fileSourceJsonEntity = generator.get();
        assertThat(fileSourceJsonEntity).isNotNull().isInstanceOf(FileSourceJsonEntity.class);
    }

    @Test
    @DisplayName("supports variant type fields in file_source_all_type.json")
    public void supports_variant_type_fields_in_file_source_all_type() {
        EntityGenerator<FileSourceJsonAllTypeEntity> generator = generatorFactory.getGenerator(FileSourceJsonAllTypeEntity.class);
        FileSourceJsonAllTypeEntity fileSourceJsonAllTypeEntity = generator.get();
        System.out.println("fileSourceJsonAllTypeEntity = " + fileSourceJsonAllTypeEntity);
        assertThat(fileSourceJsonAllTypeEntity).isNotNull().isInstanceOf(FileSourceJsonAllTypeEntity.class);
    }

    @Test
    @DisplayName("throws RuntimeException when creating an instance by loading data from empty JSON file")
    public void throw_exception_when_loading_data_from_empty_json() {
        EntityGenerator<FileSourceJsonEmptyFailEntity> generator = generatorFactory.getGenerator(FileSourceJsonEmptyFailEntity.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("throws RuntimeException when creating an instance by loading data from invalid grammar JSON file")
    public void throw_exception_when_loading_data_from_invalid_grammar_json() {
        EntityGenerator<FileSourceJsonInvalidGrammarFailEntity> generator = generatorFactory.getGenerator(FileSourceJsonInvalidGrammarFailEntity.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("throws RuntimeException when creating an instance by loading data from invalid path JSON file")
    public void throw_exception_when_loading_data_from_invalid_path_json() {
        EntityGenerator<FileSourceJsonInvalidPathFailEntity> generator = generatorFactory.getGenerator(FileSourceJsonInvalidPathFailEntity.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(RuntimeException.class);
    }
}
