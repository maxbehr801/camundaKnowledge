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
public class ThrowBusinessErrorDelegate implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(ThrowBusinessErrorDelegate.class);

    private final RuntimeService runtimeService;

    public ThrowBusinessErrorDelegate(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("TASK {}", execution.getCurrentActivityName());
        log.info("correlate error message");
        VariableMap variables = Variables.createVariables().putValue("ErrorProcess", "failed with e1");
        runtimeService.correlateMessage("eventprocesserrorMessage", execution.getProcessBusinessKey(), variables);
    }
}
