package jodag;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@SpringBootConfiguration
@ComponentScan(basePackages = "jodag")
@EnableAutoConfiguration
public class TestConfig {

}
