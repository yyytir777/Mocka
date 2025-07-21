package jodag.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jodag.generator.Generate;

@Entity
@Generate
public class Member {
    @Id
    private Long id;
    private String name;

    public Member() {

    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
