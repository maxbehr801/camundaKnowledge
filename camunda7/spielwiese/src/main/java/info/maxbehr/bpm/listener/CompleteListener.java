package info.maxbehr.bpm.listener;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

@Component
public class CompleteListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("Ich bin der Complete Listener");
    }
}
