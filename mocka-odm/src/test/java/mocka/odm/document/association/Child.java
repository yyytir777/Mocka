package mocka.odm.document.association;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Child {

    @Id
    private String id;

    private String name;
}
