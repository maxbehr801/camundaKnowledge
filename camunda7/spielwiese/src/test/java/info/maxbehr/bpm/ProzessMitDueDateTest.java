package info.maxbehr.bpm;

import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.taskQuery;

@SpringBootTest
class ProzessMitDueDateTest {

    private static final String PROCESS_KEY = "ProzessMitDueDate";
    private static final String DUE_DATE = "dueDate";

    @Autowired
    private RuntimeService runtimeService;

    @Test
    void testDueDate() {
        VariableMap variableMap = Variables.createVariables().putValue(DUE_DATE, dateIn6Hours());
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_KEY, UUID.randomUUID().toString(), variableMap);
        assertThat(processInstance).isStarted();
        Task task = taskQuery().processDefinitionKey(PROCESS_KEY).active().singleResult();
        Assertions.assertThat(task.getTaskDefinitionKey()).isEqualTo("Activity_0lb7raj");
        System.out.println(task.getDueDate());
        Assertions.assertThat(task.getDueDate()).isBeforeOrEqualTo(dateIn6Hours());
    }

    private Date dateIn6Hours() {
        return new Date(LocalDateTime.now().plusHours(6).toInstant(ZoneOffset.UTC).toEpochMilli());
    }
}
