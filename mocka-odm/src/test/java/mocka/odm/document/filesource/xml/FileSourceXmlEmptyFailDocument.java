package mocka.odm.document.filesource.xml;

import mocka.core.annotation.FileSource;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@FileSource("file-source/file_source_empty.xml")
public class FileSourceXmlEmptyFailDocument {

    @Id
    private String id;

    private String name;
    private String email;
    private String temp;

    public FileSourceXmlEmptyFailDocument() {}
}
