package info.maxbehr.bpm;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ThrowExceptionListener implements TaskListener {

    private static final Logger log = LoggerFactory.getLogger(ThrowExceptionListener.class);

    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("Ich werfe eine Exception0");
        throw new MyListenerException("meine exception");
    }
}
