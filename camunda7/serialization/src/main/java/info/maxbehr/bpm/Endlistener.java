package info.maxbehr.bpm;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class Endlistener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        SomeRecord someRecord = (SomeRecord) delegateExecution.getVariable("complex");
        System.out.println(someRecord);
    }
}
