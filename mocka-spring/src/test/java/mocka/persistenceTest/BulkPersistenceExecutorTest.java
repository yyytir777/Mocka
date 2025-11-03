package mocka.persistenceTest;

import mocka.entity.orm.Member;
import mocka.generator.EntityGenerator;
import mocka.generator.SpringGeneratorFactory;
import mocka.persistence.BulkPersistenceExecutor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;


import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BulkPersistenceExecutorTest Test Code")
@SpringBootTest
@ActiveProfiles("test")
public class BulkPersistenceExecutorTest {

    @Autowired BulkPersistenceExecutor bulkPersistenceExecutor;
    @Autowired JdbcTemplate jdbcTemplate;
    @Autowired SpringGeneratorFactory springGeneratorFactory;

    @Test
    @DisplayName("creates 1000 instances of Member.class and insert to db using batch operation")
    public void test_create_100_instances_and_bulk_persistence_success() throws InterruptedException {
        EntityGenerator<Member> generator = springGeneratorFactory.getGenerator(Member.class);
        bulkPersistenceExecutor.execute(generator, 100L, 10, Member.class);

        Long rowCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM member", Long.class);
        assertThat(rowCount).isEqualTo(100);
    }
}
