package info.maxbehr.bpm;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.spring.boot.starter.test.helper.AbstractProcessEngineRuleTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WorkflowTest extends AbstractProcessEngineRuleTest {

    @Autowired  private MessageStartEventProcessStarter starter;

    @Test
    public void shouldStartNormally() {
        ProcessInstance processInstance = starter.startNormally();
        assertThat(processInstance).isStarted().isActive().isWaitingAt("say-hello");
    }

    @Test
    public void shouldStartWithCorrelation() {
        ProcessInstance processInstance = starter.startWithMessageCorrelation();
        assertThat(processInstance).isStarted().isActive().isWaitingAt("say-hello");
    }

}
