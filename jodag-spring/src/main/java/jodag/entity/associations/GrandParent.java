package jodag.entity.associations;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jodag.generator.Generate;

import java.util.List;

@Entity
@Generate
public class GrandParent {
    @Id
    private Long id;

    private String name;

    @OneToMany
    private List<Parent> parents;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Parent> getParents() {
        return parents;
    }
}
