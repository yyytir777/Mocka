package mocka.orm.persistence;

import mocka.orm.generator.EntityGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class Producer<T> implements Callable<Void> {

    private final EntityGenerator<T> generator;
    private final BlockingQueue<List<?>> queue;
    private final int batchSize;

    public Producer(EntityGenerator<T> generator, BlockingQueue<List<?>> queue, int batchSize) {
        this.generator = generator;
        this.queue = queue;
        this.batchSize = batchSize;
    }

    @Override
    public Void call() throws Exception {
        List<T> batch = new ArrayList<>(batchSize);
        for (int i = 0; i < batchSize; i++) {
            batch.add(generator.get());
        }
        queue.put(batch);
//        System.out.printf("[Producer][%s] Produced batch size=%d%n", Thread.currentThread().getName(), batch.size());
        return null;
    }
}
