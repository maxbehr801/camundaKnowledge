package info.maxbehr.bpm;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Component;

@Component
public class SignalStarter {

    private final RuntimeService runtimeService;

    public SignalStarter(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

}
