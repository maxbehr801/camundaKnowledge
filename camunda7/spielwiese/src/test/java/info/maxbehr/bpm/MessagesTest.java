package info.maxbehr.bpm;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;

@SpringBootTest
class MessagesTest {

    public static final String THROW_MESSAGE_ID = "Throw_Message";
    public static final String CATCH_MESSAGE_ID = "Catch_Message";

    @Autowired
    RuntimeService runtimeService;

    @Test
    void testMessageWithInstance() {
        ProcessInstance catchInstance = runtimeService.startProcessInstanceByKey(CATCH_MESSAGE_ID, "asdf");
        ProcessInstance throwInstance = runtimeService.startProcessInstanceByKey(THROW_MESSAGE_ID, "asdf");

        assertThat(catchInstance).isEnded();
        assertThat(throwInstance).isEnded();
    }
}
