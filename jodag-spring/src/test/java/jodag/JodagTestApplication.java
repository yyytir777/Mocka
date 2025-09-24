package jodag;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootApplication
public class JodagTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(JodagTestApplication.class, args);
    }
}
