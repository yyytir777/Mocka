package mocka.orm.persistence;

import mocka.orm.generator.EntityGenerator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;


@Component
public class BulkPersistenceManager {

    private final ExecutorService producerExecutor;
    private final ExecutorService consumerExecutor;
    private final JdbcBatchWriter jdbcBatchWriter;
    private final BlockingQueue<List<?>> queue = new ArrayBlockingQueue<>(512);

    public BulkPersistenceManager(ThreadPoolTaskExecutor bulkProducerExecutor, ThreadPoolTaskExecutor bulkConsumerExecutor, JdbcBatchWriter jdbcBatchWriter) {
        this.producerExecutor = bulkProducerExecutor.getThreadPoolExecutor();
        this.consumerExecutor = bulkConsumerExecutor.getThreadPoolExecutor();
        this.jdbcBatchWriter = jdbcBatchWriter;
    }

    public <T> void start(EntityGenerator<T> generator, long totalCount, int batchSize, Class<T> entityType) throws InterruptedException {
        int consumerThreads = ((ThreadPoolExecutor) consumerExecutor).getCorePoolSize();
        CountDownLatch latch = new CountDownLatch(consumerThreads);
        long totalBatches = (long) Math.ceil((double) totalCount / batchSize);

        List<Callable<Void>> producers = new ArrayList<>();
        for (int i = 0; i < totalBatches; i++) {
            producers.add(new Producer<>(generator, queue, batchSize));
        }

        for(int i = 0; i < consumerThreads; i++) {
            consumerExecutor.execute(new Consumer<>(queue, jdbcBatchWriter, entityType, latch));
        }

        producerExecutor.invokeAll(producers);

        for (int i = 0; i < consumerThreads; i++) {
            queue.put(Collections.emptyList());
        }

        latch.await();
    }
}
