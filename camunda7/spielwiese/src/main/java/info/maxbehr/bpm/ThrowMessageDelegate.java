package info.maxbehr.bpm;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ThrowMessageDelegate implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(ThrowMessageDelegate.class);
    public static final String MESSAGE_NAME = "Message_Correlation";

    private final RuntimeService runtimeService;

    public ThrowMessageDelegate(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("MessageDelegate");
        MessageCorrelationResult messageCorrelationResult = runtimeService.createMessageCorrelation(MESSAGE_NAME)
                .processInstanceBusinessKey(execution.getProcessBusinessKey())
                .correlateWithResult();
        log.info("Correlation {}", messageCorrelationResult);
    }
}
