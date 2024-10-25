package info.maxbehr.bpm.delegate;

import jakarta.inject.Named;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@Named
public class RetryDelegateOne implements JavaDelegate {

    public static boolean firstAttempt = true;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        if (firstAttempt) {
            throw new BpmnError("Error-001", "It is supposed to fail.");
        }
    }
}
