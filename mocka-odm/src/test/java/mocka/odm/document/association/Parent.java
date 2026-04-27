package mocka.odm.document.association;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Parent {
    @Id
    private String id;

    private String name;

    @DBRef
    private List<Child> children;

    @DBRef
    private GrandParent grandParent;

    public String getName() {
        return name;
    }

    public List<Child> getChildren() {
        return children;
    }

    public GrandParent getGrandParent() {
        return grandParent;
    }
}
