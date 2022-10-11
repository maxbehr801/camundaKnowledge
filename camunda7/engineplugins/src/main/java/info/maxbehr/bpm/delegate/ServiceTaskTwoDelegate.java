package info.maxbehr.bpm.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ServiceTaskTwoDelegate implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(ServiceTaskTwoDelegate.class);

    public static boolean wasExecuted = false;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("2");
        wasExecuted = true;
    }
}
