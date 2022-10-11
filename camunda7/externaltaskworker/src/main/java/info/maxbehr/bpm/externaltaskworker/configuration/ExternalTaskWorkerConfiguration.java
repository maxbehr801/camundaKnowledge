package info.maxbehr.bpm.externaltaskworker.configuration;

import info.maxbehr.bpm.externaltaskworker.model.Input;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.UUID;

@Configuration
@Slf4j
public class ExternalTaskWorkerConfiguration {

    @Bean
    @ExternalTaskSubscription("externalworklambda")
    public ExternalTaskHandler externalWorkHandler() {
        return (externalTask, externalTaskService) -> {
            log.info("TaskId: " + externalTask.getId());
            Map<String, Object> allVariables = externalTask.getAllVariables();
            Input input = Input.builder()
                    .anInt((Integer) allVariables.get("integer"))
                    .aBoolean((Boolean) allVariables.get("boolean"))
                    .aString((String) allVariables.get("string"))
                    .build();
            log.info("eingelesener Input: {}", input);

            final String uuid = UUID.randomUUID().toString();
            externalTaskService.complete(externalTask, Map.of("uuid", uuid));
        };
    }
}
