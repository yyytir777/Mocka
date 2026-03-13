package mocka.odm.document.filesource.xlsx;

import mocka.core.annotation.FileSource;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@FileSource("file-source/file_source_empty.xlsx")
public class FileSourceXlsxEmptyFailDocument {

    @Id
    private String id;

    private String name;
    private String email;
    private String temp;

    public FileSourceXlsxEmptyFailDocument() {}
}
