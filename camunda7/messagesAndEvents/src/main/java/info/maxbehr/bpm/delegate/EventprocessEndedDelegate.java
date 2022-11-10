package info.maxbehr.bpm.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EventprocessEndedDelegate implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(EventprocessEndedDelegate.class);

    private final RuntimeService runtimeService;

    public EventprocessEndedDelegate(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("TASK {}", execution.getCurrentActivityName());
        VariableMap variables = Variables.createVariables().putValue("eventProcess", "ended");
        runtimeService.correlateMessage("eventProcessEndedMessage", execution.getProcessBusinessKey(), variables);
    }
}
