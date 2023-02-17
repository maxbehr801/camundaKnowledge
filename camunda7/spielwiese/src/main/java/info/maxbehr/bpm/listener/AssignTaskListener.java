package info.maxbehr.bpm.listener;

import info.maxbehr.bpm.exception.MeineCamundaException;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AssignTaskListener implements TaskListener {

    private static final Logger log = LoggerFactory.getLogger(AssignTaskListener.class);

    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("ich werfe jetzt einen Fehler");
        throw new MeineCamundaException("Exception im AssignmentTasklistener");
    }
}
