package jodag.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jodag.generator.Generate;

@Entity
@Generate
public class Demo {
    @Id
    private Long id;

    public Demo() {

    }
}
