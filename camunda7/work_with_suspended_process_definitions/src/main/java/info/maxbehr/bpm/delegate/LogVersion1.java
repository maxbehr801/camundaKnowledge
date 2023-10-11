package info.maxbehr.bpm.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogVersion1 implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(LogVersion1.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Version1");
    }
}
