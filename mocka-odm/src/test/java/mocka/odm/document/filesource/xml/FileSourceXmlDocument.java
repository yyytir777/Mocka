package mocka.odm.document.filesource.xml;

import mocka.core.annotation.FileSource;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@FileSource("file-source/file_source.xml")
public class FileSourceXmlDocument {

    @Id
    private String id;

    private String name;
    private String email;
    private String temp;

    public FileSourceXmlDocument() {}

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getTemp() { return temp; }

    @Override
    public String toString() {
        return "FileSourceXmlDocument{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", temp='" + temp + '\'' +
                '}';
    }
}
