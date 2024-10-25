package info.maxbehr.bpm;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class WorkflowTest {

    @Autowired
    public RuntimeService runtimeService;

    @Test
    void shouldExecuteHappyPath() {
        String processDefinitionKey = "externaltaskprocess";
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
        assertThat(processInstance).isStarted().isWaitingAt("ST_Process_External_Work_Lambda");
        complete(externalTask());
        assertThat(processInstance).isWaitingAt("ST_Process_External_Work_Bean");
        complete(externalTask());
        assertThat(processInstance).isWaitingAt("timer");
        managementService().executeJob(job().getId());
        assertThat(processInstance).isEnded();
    }

}
