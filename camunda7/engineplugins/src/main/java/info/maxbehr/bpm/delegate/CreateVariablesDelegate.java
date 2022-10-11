package info.maxbehr.bpm.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class CreateVariablesDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        execution.setVariable("foo", "bar");
        execution.setVariable("hello", "world");
        execution.setVariable("action-id-hist", "important");
        execution.setVariable("camunda-hist", "rocks");
    }
}
