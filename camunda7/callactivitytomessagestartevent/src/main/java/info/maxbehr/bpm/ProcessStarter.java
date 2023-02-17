package info.maxbehr.bpm;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/process")
public class ProcessStarter {

    private final String CALL_ACTIVITY_PROCESS_KEY = "CallActivityProcess";

    private final RuntimeService runtimeService;

    public ProcessStarter(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @GetMapping("/start")
    public void startProcess() {
        List<String> inputList = List.of("eins", "zwei", "drei");
        VariableMap variables = Variables.createVariables().putValue("inputList", inputList);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(CALL_ACTIVITY_PROCESS_KEY, LocalDateTime.now().toString(), variables);
    }
}
