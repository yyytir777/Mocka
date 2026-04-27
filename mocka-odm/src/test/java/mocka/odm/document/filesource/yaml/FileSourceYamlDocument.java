package mocka.odm.document.filesource.yaml;

import mocka.core.annotation.FileSource;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@FileSource("file-source/file_source.yaml")
public class FileSourceYamlDocument {

    @Id
    private String id;

    private String name;
    private String email;
    private String temp;

    public FileSourceYamlDocument() {}

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getTemp() { return temp; }

    @Override
    public String toString() {
        return "FileSourceYamlDocument{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", temp='" + temp + '\'' +
                '}';
    }
}
