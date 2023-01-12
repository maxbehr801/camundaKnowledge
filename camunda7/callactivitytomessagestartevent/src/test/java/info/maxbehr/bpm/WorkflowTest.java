package info.maxbehr.bpm;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.spring.boot.starter.test.helper.AbstractProcessEngineRuleTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WorkflowTest extends AbstractProcessEngineRuleTest {

  @Autowired
  public RuntimeService runtimeService;

  @Test
  public void shouldExecuteHappyPath() {
    // given
    final String CALL_ACTIVITY_PROCESS_KEY = "CallActivityProcess";
    final String MESSAGE_START_PROCESS_KEY = "MessageStartProcess";

    List<String> inputList = List.of("eins", "zwei");

    VariableMap variables = Variables.createVariables().putValue("inputList", inputList);

    // when
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(CALL_ACTIVITY_PROCESS_KEY, LocalDateTime.now().toString(), variables);

    // then
    assertThat(processInstance).isEnded();
  }

}
