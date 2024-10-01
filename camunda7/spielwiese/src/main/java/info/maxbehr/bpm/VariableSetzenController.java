package info.maxbehr.bpm;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/variable")
public class VariableSetzenController {

    private final RuntimeService runtimeService;

    public VariableSetzenController(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @GetMapping
    public void setVariable() {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionKey("TaskListenerProcess").singleResult();
        runtimeService.setVariable(processInstance.getId(), "abbrechen", Boolean.TRUE);
    }
}
