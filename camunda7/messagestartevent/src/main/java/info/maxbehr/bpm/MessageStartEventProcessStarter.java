package info.maxbehr.bpm;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/process")
public class MessageStartEventProcessStarter{

    public static final String STARTEVENT_MESSAGE = "StartEvent_Message";
    private final RuntimeService runtimeService;



    public MessageStartEventProcessStarter(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @GetMapping("/start/correlation")
    public ProcessInstance startWithMessageCorrelation() {
        String businessKey = UUID.randomUUID().toString();
        Map<String, Object> variables = Map.of("correlation", "correlation", "correlation2", "correlation2");
        return runtimeService.createMessageCorrelation(STARTEVENT_MESSAGE)
                .processInstanceBusinessKey(businessKey)
                .setVariables(variables)
                .correlateWithResult().getProcessInstance();
    }

    @GetMapping("/start/normally")
    public ProcessInstance startNormally() {
        String businessKey = UUID.randomUUID().toString();
        Map<String, Object> variables = Map.of("normally", "normally", "normally2", "normally2");
        return runtimeService.startProcessInstanceByMessage(STARTEVENT_MESSAGE, businessKey, variables);
    }
}
