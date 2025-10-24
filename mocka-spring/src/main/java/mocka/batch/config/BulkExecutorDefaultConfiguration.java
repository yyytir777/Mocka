package mocka.batch.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.task.ThreadPoolTaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
public class BulkExecutorDefaultConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "bulkProducerExecutor")
    public ThreadPoolTaskExecutor bulkProducerExecutor() {
        return new ThreadPoolTaskExecutorBuilder()
                .corePoolSize(8)
                .maxPoolSize(8)
                .queueCapacity(10000)
                .threadNamePrefix("Bulk-Producer-").build();
    }

    @Bean
    @ConditionalOnMissingBean(name = "bulkConsumerExecutor")
    public ThreadPoolTaskExecutor bulkConsumerExecutor() {
        return new ThreadPoolTaskExecutorBuilder()
                .corePoolSize(8)
                .maxPoolSize(8)
                .queueCapacity(0)
                .threadNamePrefix("Bulk-Consumer")
                .build();
    }
}
