package mocka.orm.persistenceTest;

import mocka.orm.entity.orm.Member;
import mocka.orm.generator.EntityGenerator;
import mocka.orm.generator.EntityGeneratorFactory;
import mocka.orm.persistence.BulkPersistenceExecutor;
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
    @Autowired
    EntityGeneratorFactory generatorFactory;

    @Test
    @DisplayName("creates 1000 instances of Member.class and insert to db using batch operation")
    public void test_create_100_instances_and_bulk_persistence_success() throws InterruptedException {
        EntityGenerator<Member> generator = generatorFactory.getGenerator(Member.class);
        bulkPersistenceExecutor.execute(generator, 100L, 10, Member.class);

        Long rowCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM member", Long.class);
    }
}
