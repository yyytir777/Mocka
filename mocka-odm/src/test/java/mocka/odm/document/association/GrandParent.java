package mocka.odm.document.association;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class GrandParent {

    @Id
    private String id;

    private String name;

    @DBRef
    private List<Parent> parents;

    public List<Parent> getParents() {
        return parents;
    }
}
