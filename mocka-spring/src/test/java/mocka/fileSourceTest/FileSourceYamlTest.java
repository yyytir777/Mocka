package mocka.fileSourceTest;

import mocka.MockaSpringTestApplication;
import mocka.entity.filesource.yaml.*;
import mocka.generator.EntityGenerator;
import mocka.generator.SpringGeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@DisplayName("FileSource YAML Test Code")
@SpringBootTest(classes = MockaSpringTestApplication.class)
public class FileSourceYamlTest {

    @Autowired SpringGeneratorFactory springGeneratorFactory;

    @Test
    @DisplayName("generates an instance by loading data from an YAML file")
    public void create_instance_by_loading_data_from_yaml() {
        EntityGenerator<FileSourceYamlEntity> generator = springGeneratorFactory.getGenerator(FileSourceYamlEntity.class);
        FileSourceYamlEntity fileSourceYamlEntity = generator.get();
        System.out.println("fileSourceYamlEntity = " + fileSourceYamlEntity);
        assertThat(fileSourceYamlEntity).isNotNull().isInstanceOf(FileSourceYamlEntity.class);
    }

    @Test
    @DisplayName("supports variant type fields in file_source_all_type.yaml")
    public void supports_variant_type_fields_in_file_source_all_type() {
        EntityGenerator<FileSourceYamlAllTypeEntity> generator = springGeneratorFactory.getGenerator(FileSourceYamlAllTypeEntity.class);
        FileSourceYamlAllTypeEntity fileSourceYamlAllTypeEntity = generator.get();
        System.out.println("fileSourceYamlAllTypeEntity = " + fileSourceYamlAllTypeEntity);
        assertThat(fileSourceYamlAllTypeEntity).isNotNull().isInstanceOf(FileSourceYamlAllTypeEntity.class);
    }

    @Test
    @DisplayName("throws RuntimeException when creating an instance by loading data from empty YAML file")
    public void throw_exception_when_loading_data_from_empty_yaml() {
        EntityGenerator<FileSourceYamlEmptyFailEntity> generator = springGeneratorFactory.getGenerator(FileSourceYamlEmptyFailEntity.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("throws RuntimeException when creating an instance by loading data from invalid grammar YAML file")
    public void throw_exception_when_loading_data_from_invalid_grammar_yaml() {
        EntityGenerator<FileSourceYamlInvalidGrammarFailEntity> generator = springGeneratorFactory.getGenerator(FileSourceYamlInvalidGrammarFailEntity.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("throws RuntimeException when creating an instance by loading data from invalid path YAML file")
    public void throw_exception_when_loading_data_from_invalid_path_yaml() {
        EntityGenerator<FileSourceYamlInvalidPathFailEntity> generator = springGeneratorFactory.getGenerator(FileSourceYamlInvalidPathFailEntity.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(RuntimeException.class);
    }
}
