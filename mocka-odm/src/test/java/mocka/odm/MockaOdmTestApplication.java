package mocka.odm;

import mocka.core.annotation.EnableMocka;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;

@EnableMocka
@ActiveProfiles("test")
@SpringBootApplication
public class MockaOdmTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MockaOdmTestApplication.class, args);
    }
}
