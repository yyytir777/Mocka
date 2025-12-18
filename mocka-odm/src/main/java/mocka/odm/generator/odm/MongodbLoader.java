package mocka.odm.generator.odm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

@Component
public class MongodbLoader implements ODMLoader {

    private static final Logger logger = LoggerFactory.getLogger(MongodbLoader.class);

    @Override
    public Set<Class<?>> load() {
        return Collections.emptySet();
    }
}
