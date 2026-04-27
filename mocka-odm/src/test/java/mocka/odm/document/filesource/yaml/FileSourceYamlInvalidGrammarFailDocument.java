package mocka.odm.document.filesource.yaml;

import mocka.core.annotation.FileSource;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@FileSource("file-source/file_source_invalid_grammar.yaml")
public class FileSourceYamlInvalidGrammarFailDocument {

    @Id
    private String id;

    private String name;
    private String email;
    private String temp;

    public FileSourceYamlInvalidGrammarFailDocument() {}
}
