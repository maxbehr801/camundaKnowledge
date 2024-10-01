package info.maxbehr.bpm;

import org.camunda.bpm.engine.MismatchingMessageCorrelationException;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.spring.boot.starter.test.helper.AbstractProcessEngineRuleTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.task;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WorkflowTest extends AbstractProcessEngineRuleTest {

    private static final String PROCESS_DEFINITION_KEY = "eventsubprocess-process";
    private static final String MESSAGE_STARTEVENTSUBPROCESS = "Message_Eventsubprocess_Start";
    private static final String BUSINESS_KEY = "businessKey";
    private static final String LOCAL_VARIABLE = "localVariable";
    private static final String LOCAL_VARIABLE_VALUE = "value";
    private static final String LOCAL_VARIABLE_VALUE_WRONG = "wrong";

    @Autowired
    public RuntimeService runtimeService;

    @Autowired
    public TaskService taskService;

    @Before
    public void init() {
        List<String> pids = runtimeService.createProcessInstanceQuery().active().list().stream().map(ProcessInstance::getProcessInstanceId).collect(Collectors.toList());
        runtimeService.deleteProcessInstances(pids, "test", true, true);
    }

    @Test
    public void correlateMessageWithBusinessKey() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY, BUSINESS_KEY);
        assertThat(processInstance).isNotNull();
        assertThat(processInstance).isStarted()
                .task()
                .hasDefinitionKey("Task_Processtask")
                .hasCandidateUser("demo")
                .isNotAssigned();
        MessageCorrelationResult messageCorrelationResult = runtimeService.createMessageCorrelation(MESSAGE_STARTEVENTSUBPROCESS)
                .processInstanceBusinessKey(BUSINESS_KEY)
                .correlateWithResult();
        assertThat(processInstance).isStarted()
                .task("Task_Eventsubprocesstask")
                .isNotAssigned();
        taskService.complete(task("Task_Processtask").getId());
        assertThat(processInstance).hasPassed("Event_Process_End");
        assertThat(processInstance).isStarted();
        taskService.complete(task("Task_Eventsubprocesstask").getId());
        assertThat(processInstance).isEnded();
    }

    @Test(expected = MismatchingMessageCorrelationException.class)
    public void doNotCorrelateMessageWithBusinessKey() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY, BUSINESS_KEY);
        assertThat(processInstance).isNotNull();
        assertThat(processInstance).isStarted()
                .task()
                .hasDefinitionKey("Task_Processtask")
                .hasCandidateUser("demo")
                .isNotAssigned();
        MessageCorrelationResult messageCorrelationResult = runtimeService.createMessageCorrelation(MESSAGE_STARTEVENTSUBPROCESS)
                .processInstanceBusinessKey(BUSINESS_KEY + "1")
                .correlateWithResult();
        taskService.complete(task("Task_Processtask").getId());
        assertThat(processInstance).isEnded();
    }

    @Test
    public void correlateMessageWithBusinessKeyAndVariable() {
        Map<String, Object> variables = new HashMap<>();
        variables.put(LOCAL_VARIABLE, LOCAL_VARIABLE_VALUE);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY, BUSINESS_KEY, variables);
        assertThat(processInstance).isNotNull();
        assertThat(processInstance).isStarted()
                .task()
                .hasDefinitionKey("Task_Processtask")
                .hasCandidateUser("demo")
                .isNotAssigned();
        MessageCorrelationResult messageCorrelationResult = runtimeService.createMessageCorrelation(MESSAGE_STARTEVENTSUBPROCESS)
                .processInstanceBusinessKey(BUSINESS_KEY)
                .localVariableEquals(LOCAL_VARIABLE, LOCAL_VARIABLE_VALUE)
                .correlateWithResult();
        assertThat(processInstance).isStarted()
                .task("Task_Eventsubprocesstask")
                .isNotAssigned();
        taskService.complete(task("Task_Eventsubprocesstask").getId());
        taskService.complete(task("Task_Processtask").getId());
        assertThat(processInstance).isEnded();
    }

    @Test(expected = MismatchingMessageCorrelationException.class)
    public void doNotCorrelateMessageWithBusinessKeyAndVariable() {
        Map<String, Object> variables = new HashMap<>();
        variables.put(LOCAL_VARIABLE, LOCAL_VARIABLE_VALUE);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY, BUSINESS_KEY, variables);
        assertThat(processInstance).isNotNull();
        assertThat(processInstance).isStarted()
                .task()
                .hasDefinitionKey("Task_Processtask")
                .hasCandidateUser("demo")
                .isNotAssigned();
        MessageCorrelationResult messageCorrelationResult = runtimeService.createMessageCorrelation(MESSAGE_STARTEVENTSUBPROCESS)
                .processInstanceBusinessKey(BUSINESS_KEY)
                .localVariableEquals(LOCAL_VARIABLE, LOCAL_VARIABLE_VALUE_WRONG)
                .correlateWithResult();
    }

    @Test
    public void shouldExecuteHappyPath() {
        // when
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY);

        // then
        assertThat(processInstance).isStarted()
                .task()
                .hasDefinitionKey("Task_Processtask")
                .hasCandidateUser("demo")
                .isNotAssigned();
        taskService.complete(task("Task_Processtask").getId());
        assertThat(processInstance).isEnded();
    }

}
