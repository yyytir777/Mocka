package mocka.odm.document.filesource.csv;

import mocka.core.annotation.FileSource;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@FileSource("file-source/file_source.csv")
public class FileSourceCsvDocument {

    @Id
    private String id;

    private String name;
    private String email;
    private String tel;

    public FileSourceCsvDocument() {}

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getTel() { return tel; }

    @Override
    public String toString() {
        return "FileSourceCsvDocument{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
