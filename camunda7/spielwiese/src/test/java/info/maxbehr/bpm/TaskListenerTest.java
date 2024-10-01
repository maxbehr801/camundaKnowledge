package info.maxbehr.bpm;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class TaskListenerTest {

    public static final String TASK_LISTENER_PROCESS = "TaskListenerProcess";
    public static final String USER_TASK_ID = "Mein_UserTask";

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Test
    void testListeners() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TASK_LISTENER_PROCESS);
        assertThat(processInstance).isWaitingAt(USER_TASK_ID);
        runtimeService.setVariable(processInstance.getProcessInstanceId(), "abbrechen", Boolean.TRUE);
        assertThat(processInstance).isEnded();
    }
}
