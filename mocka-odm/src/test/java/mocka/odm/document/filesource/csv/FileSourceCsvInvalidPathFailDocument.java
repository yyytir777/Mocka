package mocka.odm.document.filesource.csv;

import mocka.core.annotation.FileSource;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@FileSource("file-source/file_source_invalid_path.csv")
public class FileSourceCsvInvalidPathFailDocument {

    @Id
    private String id;

    private String name;
    private String email;
    private String tel;

    public FileSourceCsvInvalidPathFailDocument() {}
}
