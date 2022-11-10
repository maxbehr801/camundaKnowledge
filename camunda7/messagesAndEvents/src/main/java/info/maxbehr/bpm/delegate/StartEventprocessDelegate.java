package info.maxbehr.bpm.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.Variables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class StartEventprocessDelegate implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(StartEventprocessDelegate.class);

    private final RuntimeService runtimeService;

    public StartEventprocessDelegate(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("TASK {}", execution.getCurrentActivityName());
        Map<String, Object> variables = Variables.createVariables().putValue("startprocess", "starterprocess");
        ProcessInstance startProcessInstanceByMessage = runtimeService.startProcessInstanceByMessage("StartProcess_Message", execution.getProcessBusinessKey(), variables);
        log.info("started {}", startProcessInstanceByMessage.getProcessInstanceId());
    }
}
