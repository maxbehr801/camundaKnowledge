package info.maxbehr.process.camunda8.configuration;

import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableZeebeClient
@Deployment(resources = {"classpath*:**.bpmn", "classpath*:**.dmn"})
public class Camunda8Configuration {
}
