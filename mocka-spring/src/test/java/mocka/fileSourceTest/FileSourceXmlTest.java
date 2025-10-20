package mocka.fileSourceTest;

import mocka.MockaSpringTestApplication;
import mocka.entity.filesource.xml.*;
import mocka.generator.EntityGenerator;
import mocka.generator.SpringGeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@DisplayName("FileSource XML Test Code")
@SpringBootTest(classes = MockaSpringTestApplication.class)
public class FileSourceXmlTest {

    @Autowired SpringGeneratorFactory springGeneratorFactory;

    @Test
    @DisplayName("generates an instance by loading data from an XML file")
    public void create_instance_by_loading_data_from_xml() {
        EntityGenerator<FileSourceXmlEntity> generator = springGeneratorFactory.getGenerator(FileSourceXmlEntity.class);
        FileSourceXmlEntity fileSourceXmlEntity = generator.get();
        assertThat(fileSourceXmlEntity).isNotNull().isInstanceOf(FileSourceXmlEntity.class);
    }

    @Test
    @DisplayName("supports variant type fields in file_source_all_type.xml")
    public void supports_variant_type_fields_in_file_source_all_type() {
        EntityGenerator<FileSourceXmlAllTypeEntity> generator = springGeneratorFactory.getGenerator(FileSourceXmlAllTypeEntity.class);
        FileSourceXmlAllTypeEntity fileSourceXmlAllTypeEntity = generator.get();
        System.out.println("fileSourceXmlAllTypeEntity = " + fileSourceXmlAllTypeEntity);
        assertThat(fileSourceXmlAllTypeEntity).isNotNull().isInstanceOf(FileSourceXmlAllTypeEntity.class);
    }

    @Test
    @DisplayName("throws RuntimeException when creating an instance by loading data from empty XML file")
    public void throw_exception_when_loading_data_from_empty_xml() {
        EntityGenerator<FileSourceXmlEmptyFailEntity> generator = springGeneratorFactory.getGenerator(FileSourceXmlEmptyFailEntity.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("throws RuntimeException when creating an instance by loading data from invalid grammar XML file")
    public void throw_exception_when_loading_data_from_invalid_grammar_xml() {
        EntityGenerator<FileSourceXmlInvalidGrammarFailEntity> generator = springGeneratorFactory.getGenerator(FileSourceXmlInvalidGrammarFailEntity.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("throws RuntimeException when creating an instance by loading data from invalid path XML file")
    public void throw_exception_when_loading_data_from_invalid_path_xml() {
        EntityGenerator<FileSourceXmlInvalidPathFailEntity> generator = springGeneratorFactory.getGenerator(FileSourceXmlInvalidPathFailEntity.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(RuntimeException.class);
    }
}
