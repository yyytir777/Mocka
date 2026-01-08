package mocka.orm.entity.filesource.csv;


import mocka.orm.annotation.FileSource;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@FileSource("file-source/file_source_empty.csv")
public class FileSourceCsvEmptyFailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String tel;

    public FileSourceCsvEmptyFailEntity() {}

    public FileSourceCsvEmptyFailEntity(Long id, String name, String email, String tel) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.tel = tel;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTel(String temp) {
        this.tel = temp;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getTel() {
        return tel;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "FileSourceEntity{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", temp='" + tel + '\'' +
                '}';
    }
}
