
package info.maxbehr.bpm;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class WorkflowTest {

  @Autowired
  private RuntimeService runtimeService;
  @Autowired
  private HistoryService historyService;

  @Test
  void shouldExecuteHappyPath() {
    // given
    final String CALL_ACTIVITY_PROCESS_KEY = "CallActivityProcess";
    final String MESSAGE_START_PROCESS_KEY = "MessageStartProcess";

    List<String> inputList = List.of("eins", "zwei", "drei");

    VariableMap variables = Variables.createVariables().putValue("inputList", inputList);

    // when
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(CALL_ACTIVITY_PROCESS_KEY, LocalDateTime.now().toString(), variables);
    assertThat(processInstance).isStarted();
    List<HistoricProcessInstance> completedInstances = historyService.createHistoricProcessInstanceQuery().processDefinitionKey(MESSAGE_START_PROCESS_KEY).list();

    // then
    assertThat(completedInstances).hasSize(3);
    assertThat(processInstance).isEnded();
  }

}
