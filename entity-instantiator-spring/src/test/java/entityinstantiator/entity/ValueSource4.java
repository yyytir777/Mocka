package entityinstantiator.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import entityinstantiator.annotation.ValueSource;

@Entity
public class ValueSource4 {

    @Id
    private Long id;

    @ValueSource(path = "asd.txt", type = String.class)
    private String name;

    public String getName() {
        return name;
    }
}
