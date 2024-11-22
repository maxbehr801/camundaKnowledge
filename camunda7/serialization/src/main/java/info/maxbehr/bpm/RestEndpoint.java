package info.maxbehr.bpm;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class RestEndpoint {

    private static final String PROCESS_DEFINITION_KEY = "serializationprocess";

    private final RuntimeService runtimeService;

    public RestEndpoint(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @GetMapping("/")
    public void variables() {
        SomeRecord someRecord = new SomeRecord("First", "Name", 42, true);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY);
        runtimeService.setVariable(processInstance.getId(), "hello", "world");
        runtimeService.setVariable(processInstance.getId(), "complex", someRecord);
    }


}
