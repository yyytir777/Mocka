package mocka.odm.document.filesource.json;

import mocka.core.annotation.FileSource;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@FileSource("file-source/file_source_invalid_path.json")
public class FileSourceJsonInvalidPathFailDocument {

    @Id
    private String id;

    private String name;
    private String email;
    private String temp;

    public FileSourceJsonInvalidPathFailDocument() {}
}
