package mocka.orm.entity.valuesource;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import mocka.orm.annotation.ValueSource;

@Entity
public class ValueSourceInvalidMatchFail {

    @Id
    private Long id;

    @ValueSource(generatorKey = "invalid_match.txt")
    private String name;

    public String getName() {
        return name;
    }
}
