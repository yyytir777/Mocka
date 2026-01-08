package mocka.orm.persistence;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Consumer<T> implements Runnable {

    private final BlockingQueue<List<?>> queue;
    private final JdbcBatchWriter jdbcBatchWriter;
    private final Class<T> entityType;
    private final CountDownLatch latch;

    public Consumer(BlockingQueue<List<?>> queue, JdbcBatchWriter jdbcBatchWriter, Class<T> entityType, CountDownLatch latch) {
        this.queue = queue;
        this.jdbcBatchWriter = jdbcBatchWriter;
        this.entityType = entityType;
        this.latch = latch;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        try {
            while (true) {
                List<T> batch = (List<T>) queue.take();
                if (batch.isEmpty()) break;
                jdbcBatchWriter.insertBatch(entityType, batch);
//                System.out.printf("[Consumer][%s] Inserted batch size=%d%n", Thread.currentThread().getName(), batch.size());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            latch.countDown();
        }
    }
}
