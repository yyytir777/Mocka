package entityinstantiator.fileSourceTest;

import entityinstantiator.EntityInstantiatorSpringTestApplication;
import entityinstantiator.entity.filesource.json.FileSourceJsonEmptyFailEntity;
import entityinstantiator.entity.filesource.json.FileSourceJsonEntity;
import entityinstantiator.entity.filesource.json.FileSourceJsonInvalidGrammarFailEntity;
import entityinstantiator.entity.filesource.json.FileSourceJsonInvalidPathFailEntity;
import entityinstantiator.entity.filesource.xml.FileSourceXmlEmptyFailEntity;
import entityinstantiator.entity.filesource.xml.FileSourceXmlEntity;
import entityinstantiator.entity.filesource.xml.FileSourceXmlInvalidGrammarFailEntity;
import entityinstantiator.entity.filesource.xml.FileSourceXmlInvalidPathFailEntity;
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
@DisplayName("FileSource JSON Test Code")
@SpringBootTest(classes = EntityInstantiatorSpringTestApplication.class)
public class FileSourceJsonTest {

    @Autowired SpringGeneratorFactory springGeneratorFactory;

    @Test
    @DisplayName("generates an instance by loading data from an JSON file")
    public void create_instance_by_loading_data_from_json() {
        EntityGenerator<FileSourceJsonEntity> generator = springGeneratorFactory.getGenerator(FileSourceJsonEntity.class);
        FileSourceJsonEntity fileSourceJsonEntity = generator.get();
        assertThat(fileSourceJsonEntity).isNotNull().isInstanceOf(FileSourceJsonEntity.class);
    }

    @Test
    @DisplayName("throws RuntimeException when creating an instance by loading data from empty JSON file")
    public void throw_exception_when_loading_data_from_empty_json() {
        EntityGenerator<FileSourceJsonEmptyFailEntity> generator = springGeneratorFactory.getGenerator(FileSourceJsonEmptyFailEntity.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("throws RuntimeException when creating an instance by loading data from invalid grammar JSON file")
    public void throw_exception_when_loading_data_from_invalid_grammar_json() {
        EntityGenerator<FileSourceJsonInvalidGrammarFailEntity> generator = springGeneratorFactory.getGenerator(FileSourceJsonInvalidGrammarFailEntity.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("throws RuntimeException when creating an instance by loading data from invalid path JSON file")
    public void throw_exception_when_loading_data_from_invalid_path_json() {
        EntityGenerator<FileSourceJsonInvalidPathFailEntity> generator = springGeneratorFactory.getGenerator(FileSourceJsonInvalidPathFailEntity.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(RuntimeException.class);
    }
}
