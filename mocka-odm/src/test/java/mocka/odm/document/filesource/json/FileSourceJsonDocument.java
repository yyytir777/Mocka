package mocka.odm.document.filesource.json;

import mocka.core.annotation.FileSource;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@FileSource("file-source/file_source.json")
public class FileSourceJsonDocument {

    @Id
    private String id;

    private String name;
    private String email;
    private String temp;

    public FileSourceJsonDocument() {}

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getTemp() { return temp; }

    @Override
    public String toString() {
        return "FileSourceJsonDocument{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", temp='" + temp + '\'' +
                '}';
    }
}
