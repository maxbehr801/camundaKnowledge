package info.maxbehr.workflow;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FormApplication {

  public static void main(String... args) {
    SpringApplication.run(FormApplication.class, args);
  }

}