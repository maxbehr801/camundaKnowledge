package info.maxbehr.bpm.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ErgebnisLoggen implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(ErgebnisLoggen.class);

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Integer ergebnis1 = (Integer) delegateExecution.getVariable("ergebnis1");
        Integer ergebnis2 = (Integer) delegateExecution.getVariable("ergebnis2");
        log.info("Ergebnis {}, {}", ergebnis1, ergebnis2);
    }
}
