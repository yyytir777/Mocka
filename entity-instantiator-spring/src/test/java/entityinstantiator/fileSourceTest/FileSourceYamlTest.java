package entityinstantiator.fileSourceTest;

import entityinstantiator.EntityInstantiatorSpringTestApplication;
import entityinstantiator.entity.filesource.csv.FileSourceCsvEmptyFailEntity;
import entityinstantiator.entity.filesource.csv.FileSourceCsvEntity;
import entityinstantiator.entity.filesource.csv.FileSourceCsvInvalidPathFailEntity;
import entityinstantiator.entity.filesource.xml.FileSourceXmlInvalidGrammarFailEntity;
import entityinstantiator.entity.filesource.yaml.FileSourceYamlEmptyFailEntity;
import entityinstantiator.entity.filesource.yaml.FileSourceYamlEntity;
import entityinstantiator.entity.filesource.yaml.FileSourceYamlInvalidGrammarFailEntity;
import entityinstantiator.entity.filesource.yaml.FileSourceYamlInvalidPathFailEntity;
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
@DisplayName("FileSource YAML Test Code")
@SpringBootTest(classes = EntityInstantiatorSpringTestApplication.class)
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
