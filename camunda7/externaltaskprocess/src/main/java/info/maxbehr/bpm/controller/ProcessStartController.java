package info.maxbehr.bpm.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/process")
public class ProcessStartController {

    private static final String PROCESS_KEY = "externaltaskprocess";

    private final RuntimeService runtimeService;

    public ProcessStartController(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @GetMapping("/start")
    public String startProcessInstance() {
        final String businessKey = UUID.randomUUID().toString();
        Map<String, Object> variables = Map.of("string", "string", "integer", 1, "boolean", true);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_KEY, businessKey, variables);
        return processInstance.getId();
    }
}
