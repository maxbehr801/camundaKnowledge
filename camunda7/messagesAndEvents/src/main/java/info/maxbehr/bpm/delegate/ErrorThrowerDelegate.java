package info.maxbehr.bpm.delegate;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ErrorThrowerDelegate implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(ErrorThrowerDelegate.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("TASK {}", execution.getCurrentActivityName());
        double outcome = Math.random();
        if (outcome < 0.33) {
            log.info("e1");
            execution.setVariable("errorThrower", "e1");
            throw new BpmnError("e1");
        }
        if (outcome >= 0.33 && outcome < 0.66) {
            log.info("e2");
            execution.setVariable("errorThrower", "e2");
            throw new BpmnError("e2");
        }
        execution.setVariable("errorThrower", "no error");
        log.info("no error");
    }
}
