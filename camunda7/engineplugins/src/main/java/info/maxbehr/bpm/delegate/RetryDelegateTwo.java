package info.maxbehr.bpm.delegate;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.inject.Named;

@Component
@Named
public class RetryDelegateTwo implements JavaDelegate {

    public static boolean firstAttempt = true;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        if (firstAttempt) {
            throw new BpmnError("It is supposed to fail.");
        }
    }
}
