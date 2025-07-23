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
    private String email;
    private MemberType type;

    public Member() {

    }

    public Long getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }

    public Member(Long id, String name, String email, MemberType type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", type=" + type +
                '}';
    }
}
