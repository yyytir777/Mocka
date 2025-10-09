package entityinstantiator;


import entityinstantiator.annotation.EnableEntityInstantiator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;

@EnableEntityInstantiator
@ActiveProfiles("test")
@SpringBootApplication
public class EntityInstantiatorSpringTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(EntityInstantiatorSpringTestApplication.class, args);
    }
}
