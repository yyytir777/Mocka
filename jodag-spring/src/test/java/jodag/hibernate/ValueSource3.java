package jodag.hibernate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jodag.annotation.ValueSource;

@Entity
public class ValueSource3 {

    @Id
    private Long id;

    @ValueSource(path = "test.txt", type = String.class, generatorKey = "test1")
    private String name;

    public String getName() {
        return name;
    }
}
