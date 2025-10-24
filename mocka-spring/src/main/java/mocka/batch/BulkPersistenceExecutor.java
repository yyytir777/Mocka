package mocka.batch;

import mocka.generator.EntityGenerator;
import org.springframework.stereotype.Component;

@Component
public class BulkPersistenceExecutor {

    private final BulkPersistenceManager bulkPersistenceManager;

    public BulkPersistenceExecutor(BulkPersistenceManager bulkPersistenceManager) {
        this.bulkPersistenceManager = bulkPersistenceManager;
    }

    public <T> void execute(EntityGenerator<T> generator, Long count, int batchSize, Class<T> entityType) throws InterruptedException {
        bulkPersistenceManager.start(generator, count, batchSize, entityType);
    }
}
