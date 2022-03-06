package boards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "security")
public class BoardsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BoardsServiceApplication.class, args);
    }
}
