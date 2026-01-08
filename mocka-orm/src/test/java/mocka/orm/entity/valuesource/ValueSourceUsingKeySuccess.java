package mocka.orm.entity.valuesource;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import mocka.orm.annotation.ValueSource;

@Entity
public class ValueSourceUsingKeySuccess {

    @Id
    private Long id;

    @ValueSource(path = "test.txt", type = String.class)
    private String name;

    public String getName() {
        return name;
    }
}
