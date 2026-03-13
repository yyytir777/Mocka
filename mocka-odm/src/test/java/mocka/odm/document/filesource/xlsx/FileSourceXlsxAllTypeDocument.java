package mocka.odm.document.filesource.xlsx;

import mocka.core.annotation.FileSource;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document
@FileSource("file-source/file_source_all_type.xlsx")
public class FileSourceXlsxAllTypeDocument {

    @Id
    private String id;

    private String name;
    private String email;
    private LocalDate localDate;
    private LocalDateTime localDateTime;

    public FileSourceXlsxAllTypeDocument() {}

    public String getName() { return name; }
    public String getEmail() { return email; }
    public LocalDate getLocalDate() { return localDate; }

    @Override
    public String toString() {
        return "FileSourceXlsxAllTypeDocument{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", localDate=" + localDate +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
