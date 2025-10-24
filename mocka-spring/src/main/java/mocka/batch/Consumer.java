package mocka.batch;

import mocka.batch.persistence.JdbcBatchPersistence;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Consumer<T> implements Runnable {

    private final BlockingQueue<List<?>> queue;
    private final JdbcBatchPersistence jdbcBatchPersistence;
    private final Class<T> entityType;

    public Consumer(BlockingQueue<List<?>> queue, JdbcBatchPersistence jdbcBatchPersistence, Class<T> entityType) {
        this.queue = queue;
        this.jdbcBatchPersistence = jdbcBatchPersistence;
        this.entityType = entityType;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        try {
            while (true) {
                List<T> batch = (List<T>) queue.take();
                if (batch.isEmpty()) break;
                jdbcBatchPersistence.insertBatch(entityType, batch);
//                System.out.printf("[Consumer][%s] Inserted batch size=%d%n", Thread.currentThread().getName(), batch.size());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
