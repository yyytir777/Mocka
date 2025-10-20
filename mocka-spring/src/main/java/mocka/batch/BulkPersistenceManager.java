package mocka.batch;

import mocka.batch.persistence.JdbcBatchPersistence;
import mocka.generator.EntityGenerator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;


@Component
public class BulkPersistenceManager<T> {

    private final ThreadPoolTaskExecutor producerExecutor;
    private final ThreadPoolTaskExecutor consumerExecutor;
    private final JdbcBatchPersistence jdbcBatchPersistence;
    private final BlockingQueue<List<T>> queue = new ArrayBlockingQueue<>(48);

    public BulkPersistenceManager(ThreadPoolTaskExecutor bulkProducerExecutor, ThreadPoolTaskExecutor bulkConsumerExecutor, JdbcBatchPersistence jdbcBatchPersistence) {
        this.producerExecutor = bulkProducerExecutor;
        this.consumerExecutor = bulkConsumerExecutor;
        this.jdbcBatchPersistence = jdbcBatchPersistence;
    }

    public void start(EntityGenerator<T> generator, long totalCount, Class<T> entityType) throws InterruptedException {
        int batchSize = 10000;
        int consumerThreads = consumerExecutor.getCorePoolSize();

        CountDownLatch producerLatch = new CountDownLatch((int) Math.ceil((double) totalCount / batchSize));
        CountDownLatch consumerLatch = new CountDownLatch(consumerThreads);

        // 소비자 스레드 시작
        for (int i = 0; i < consumerThreads; i++) {
            consumerExecutor.execute(() -> {
                try {
                    while (true) {
                        List<T> batch = queue.take();
                        if (batch.isEmpty()) break; // 빈 리스트를 받으면 -> finally
                        jdbcBatchPersistence.insertBatch(entityType, batch);
                        System.out.printf("[Consumer][%s] Inserted batch size=%d%n",
                                Thread.currentThread().getName(), batch.size());
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    consumerLatch.countDown();
                }
            });
        }

        // 생산자 스레드 시작
        for (long i = 0; i < totalCount; i += batchSize) {
            producerExecutor.execute(() -> {
                List<T> batch = new ArrayList<>(batchSize);
                for (int j = 0; j < batchSize; j++) {
                    batch.add(generator.get());
                }
                try {
                    queue.put(batch);
                    System.out.printf("[Producer][%s] Produced batch size=%d%n",
                            Thread.currentThread().getName(), batch.size());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    producerLatch.countDown();
                }
            });
        }

        // 모든 생성자 스레드 종료까지 메인 스레드는 대기
        producerLatch.await();

        // 소비자 스레드에게 생성이 종료되었음을 전달
        for (int i = 0; i < consumerThreads; i++) {
            queue.put(Collections.emptyList());
        }

        // 모든 소비자 스레드 종료까지 메인 스레드는 대기
        consumerLatch.await();


        shutdownExecutors();
    }

    private void shutdownExecutors() {
        producerExecutor.shutdown();
        consumerExecutor.shutdown();
    }
}
