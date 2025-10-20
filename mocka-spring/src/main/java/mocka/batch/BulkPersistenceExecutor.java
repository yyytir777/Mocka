//package mocka.batch;
//
//import mocka.generator.EntityGenerator;
//import org.springframework.stereotype.Component;
//
//@Component
//public class BulkPersistenceExecutor<T> {
//
//    private final BulkPersistenceManager<T> bulkPersistenceManager;
//
//    public BulkPersistenceExecutor(BulkPersistenceManager<T> bulkPersistenceManager) {
//        this.bulkPersistenceManager = bulkPersistenceManager;
//    }
//
//    public void execute(EntityGenerator<T> generator, Long count, Class<T> entityType) throws InterruptedException {
//        bulkPersistenceManager.start(generator, count, entityType);
//    }
//}
