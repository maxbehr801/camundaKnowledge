package info.maxbehr.bpm.externaltaskworker.bean;

import info.maxbehr.bpm.externaltaskworker.model.Input;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@ExternalTaskSubscription(topicName = "externalworkbean", autoOpen = false)
public class TaskWorker implements ExternalTaskHandler {

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        log.info("TaskId: " + externalTask.getId());
        Map<String, Object> allVariables = externalTask.getAllVariables();
        Input input = Input.builder()
                .anInt((Integer) allVariables.get("integer"))
                .aBoolean((Boolean) allVariables.get("boolean"))
                .aString((String) allVariables.get("string"))
                .build();
        log.info("eingelesener Input: {} und UUID: {}", input, (String) allVariables.get("uuid"));

        final String variable = "weitere Variable";
        externalTaskService.complete(externalTask, Map.of("variable", variable));
    }
}

