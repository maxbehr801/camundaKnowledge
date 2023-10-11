package info.maxbehr.bpm.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StartSuspensionProcess implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(StartSuspensionProcess.class);

    private final RuntimeService runtimeService;

    public StartSuspensionProcess(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        MessageCorrelationResult messageStartSuspensionProcess = runtimeService
                .createMessageCorrelation("Message_Start_Suspension_Process")
                .processInstanceBusinessKey(execution.getProcessBusinessKey())
                .correlateWithResult();
        log.info("Started Process {}", messageStartSuspensionProcess);
    }
}
