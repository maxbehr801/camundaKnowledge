package info.maxbehr.bpm.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MessageStartEventListener implements ExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(MessageStartEventListener.class);

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String processDefinitionId = execution.getProcessDefinitionId();
        log.info("processDefinitionId {}", processDefinitionId);
    }
}
