package info.maxbehr.workflow.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogVariablesDelegate implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(LogVariablesDelegate.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("GENERIC");
        log.info("Name {}", execution.getVariable("FormField_Name"));
        log.info("Vorname {}", execution.getVariable("FormField_Vorname"));
        log.info("Score {}", execution.getVariable("FormField_Score"));
        log.info("RoundEnded? {}", execution.getVariable("FormField_RoundEnded"));
        log.info("Date {}", execution.getVariable("FormField_Date"));
        log.info("MODELER");
        log.info("Name {}", execution.getVariable("modeler_name"));
        log.info("Vorname {}", execution.getVariable("modeler_vorname"));
        log.info("Score {}", execution.getVariable("modeler_score"));
        log.info("roundEnded {}", execution.getVariable("modeler_roundEnded"));
        log.info("Select {}", execution.getVariable("modeler_select"));
    }
}
