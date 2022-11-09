package info.maxbehr.bpm.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CreateVariablesDelegate implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(CreateVariablesDelegate.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Create variables");
        execution.setVariables(Map.of("Variable1", 1, "Variable2", 2));
    }
}
