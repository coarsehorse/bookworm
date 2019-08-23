package bookworm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookWormStarter {

    public static void main(String[] args) {
        SpringApplication.run(BookWormStarter.class, args);
    }
}
