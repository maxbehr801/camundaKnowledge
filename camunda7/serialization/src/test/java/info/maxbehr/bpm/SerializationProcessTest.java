package info.maxbehr.bpm;

import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.junit5.ProcessEngineExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ProcessEngineExtension.class)
class SerializationProcessTest {

    private static final String PROCESS_DEFINITION_KEY = "serializationprocess";

    @Test
    @Deployment(resources = "serialization.bpmn")
    void test() {
        assertThat(true).isTrue();
    }

}
