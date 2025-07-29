package jodag.entity;

import jakarta.persistence.*;
import jodag.annotation.Email;
import jodag.annotation.LoremIpsum;
import jodag.generator.Generate;

@Entity
@Generate
public class TestEntity {

    @Id
    private Long id;

    @Column(length = 128)
    @LoremIpsum
    private String name;

    @Column
    @Email
    private String email;

    @Column
    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    public TestEntity() {

    }

    public TestEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TestEntity(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "TestEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
