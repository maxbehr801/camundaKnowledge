package info.maxbehr.bpm.workerswithvariables;

import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Deployment(resources = "classpath:worker-with-variables.bpmn")
public class WorkersWithVariablesApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkersWithVariablesApplication.class, args);
    }

}
