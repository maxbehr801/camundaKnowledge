package info.maxbehr.bpm;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.processDefinition;

@SpringBootTest
class CorrelateMessageStartEventProcessTest {

    static final String PROCESS_ID = "Correlate_Message_Start_Event";
    static final String START_MESSAGE_EVENT_NAME = "Start_Message_Event";

    @Autowired
    RuntimeService runtimeService;

    @Test
    void testCorrelationWithoutInstance() {
        runtimeService.correlateMessage(START_MESSAGE_EVENT_NAME, UUID.randomUUID().toString());
        assertThat(processDefinition(PROCESS_ID)).hasActiveInstances(1);
    }
}
