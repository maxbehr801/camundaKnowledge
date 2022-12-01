package info.maxbehr.bpm.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogListener implements ExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(LogListener.class);
    public static final String EINE_VARIABLE_123 = "eineVariable123";

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        log.info("Variable: {}", execution.getVariable(EINE_VARIABLE_123));
    }
}
