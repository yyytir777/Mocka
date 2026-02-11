package mocka.orm.persistence;

import mocka.core.generator.Generator;
import mocka.orm.generator.EntityGenerator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;


@Component
public class BulkPersistenceManager {

    private final ExecutorService producerExecutor;
    private final ExecutorService consumerExecutor;
    private final JdbcBatchWriter jdbcBatchWriter;

    public BulkPersistenceManager(ThreadPoolTaskExecutor bulkProducerExecutor, ThreadPoolTaskExecutor bulkConsumerExecutor, JdbcBatchWriter jdbcBatchWriter) {
        this.producerExecutor = bulkProducerExecutor.getThreadPoolExecutor();
        this.consumerExecutor = bulkConsumerExecutor.getThreadPoolExecutor();
        this.jdbcBatchWriter = jdbcBatchWriter;
    }

    public <T> void start(Generator<T> generator, long totalCount, int batchSize, Class<T> type) {
        long totalBatches = (long) Math.ceil((double) totalCount / batchSize);

        List<CompletableFuture<Void>> futures = LongStream.range(0, totalBatches)
                .mapToObj(batchIdx -> {
                    int currentBatchSize = calculateBatchSize(batchIdx, totalBatches, totalCount, batchSize);
                    return CompletableFuture
                            .supplyAsync(() -> generateBatch(generator, currentBatchSize), producerExecutor) // run async logic (in producerExecutor)
                            .thenAcceptAsync(batch -> jdbcBatchWriter.insertBatch(type, batch), consumerExecutor); // run insert async logic (in consumerExecutor)
                }).toList();

        try {
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        } catch (Exception e) {
            futures.forEach(future -> future.cancel(true));
            throw new  RuntimeException("Bulk persistence failed", e);
        }
    }

    private <T> List<T> generateBatch(Generator<T> generator, int batchSize) {
        return IntStream.range(0, batchSize)
                .mapToObj(i -> generator.get())
                .toList();
    }

    private int calculateBatchSize(long batchIdx, long totalBatches, long totalCount, int batchSize) {
        if(batchIdx == totalBatches - 1) {
            long remaining = totalCount % batchSize;
            return remaining == 0 ? batchSize : (int) remaining;
        }
        return batchSize;
    }
}
