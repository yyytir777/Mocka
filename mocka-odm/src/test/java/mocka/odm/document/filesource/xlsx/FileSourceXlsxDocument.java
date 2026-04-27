package mocka.odm.document.filesource.xlsx;

import mocka.core.annotation.FileSource;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@FileSource("file-source/file_source.xlsx")
public class FileSourceXlsxDocument {

    @Id
    private String id;

    private String name;
    private String email;
    private String temp;

    public FileSourceXlsxDocument() {}

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getTemp() { return temp; }

    @Override
    public String toString() {
        return "FileSourceXlsxDocument{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", temp='" + temp + '\'' +
                '}';
    }
}
