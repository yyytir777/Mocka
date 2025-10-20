package mocka;


import mocka.annotation.EnableMocka;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;

@EnableMocka
@ActiveProfiles("test")
@SpringBootApplication
public class MockaSpringTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MockaSpringTestApplication.class, args);
    }
}
