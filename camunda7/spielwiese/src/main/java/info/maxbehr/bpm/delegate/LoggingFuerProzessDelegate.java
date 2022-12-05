package info.maxbehr.bpm.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingFuerProzessDelegate implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(LoggingFuerProzessDelegate.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info(execution.getProcessDefinitionId());
        log.info(((ExecutionEntity) execution).getProcessDefinition().getKey());
    }
}
