package info.maxbehr.bpm;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LogProcessTest {

    private static final String PROZESS = "LogProcess";

    @Autowired
    private RuntimeService runtimeService;

    @Test
    void testLogTest() {
        runtimeService.startProcessInstanceByKey(PROZESS);
    }
}
