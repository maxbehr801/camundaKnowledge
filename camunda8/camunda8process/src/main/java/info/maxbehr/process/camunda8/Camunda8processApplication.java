package info.maxbehr.process.camunda8;

import io.camunda.zeebe.spring.client.EnableZeebeClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Camunda8processApplication {

    public static void main(String[] args) {
        SpringApplication.run(Camunda8processApplication.class, args);
    }

}
