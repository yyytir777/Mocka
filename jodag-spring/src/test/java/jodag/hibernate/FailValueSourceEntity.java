package jodag.hibernate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jodag.annotation.ValueSource;

@Entity
public class FailValueSourceEntity {

    @Id
    private Long id;

    @ValueSource(path = "no.txt", type = String.class, generatorKey = "ㅁㄴㅇㄹ")
    private String description;

    public String getDescription() {
        return description;
    }
}
