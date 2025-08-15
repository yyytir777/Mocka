package jodag.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class NonGenerateEntity {
    @Id
    private Long id;

    private String name;

    public NonGenerateEntity() {

    }

    public NonGenerateEntity(String name) {
        this.name = name;
    }
}
