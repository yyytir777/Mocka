package mocka.odm.document.filesource.json;

import mocka.core.annotation.FileSource;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@FileSource("file-source/file_source_invalid_grammar.json")
public class FileSourceJsonInvalidGrammarFailDocument {

    @Id
    private String id;

    private String name;
    private String email;
    private String temp;

    public FileSourceJsonInvalidGrammarFailDocument() {}
}
