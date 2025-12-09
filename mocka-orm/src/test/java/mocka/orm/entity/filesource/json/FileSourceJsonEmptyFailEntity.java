package mocka.orm.entity.filesource.json;


import mocka.orm.annotation.FileSource;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@FileSource("file-source/file_source_empty.json")
public class FileSourceJsonEmptyFailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String temp;

    public FileSourceJsonEmptyFailEntity() {}

    public FileSourceJsonEmptyFailEntity(Long id, String name, String email, String temp) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.temp = temp;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTemp(String temp) {
        this.temp = temp;
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

    public String getTemp() {
        return temp;
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
                ", temp='" + temp + '\'' +
                '}';
    }
}
