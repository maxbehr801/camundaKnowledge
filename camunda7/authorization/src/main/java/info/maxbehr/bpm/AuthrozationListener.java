package info.maxbehr.bpm;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

@Component
public class AuthrozationListener implements TaskListener {

    private final AuthorizationService authService;

    public AuthrozationListener(AuthorizationService authService) {
        this.authService = authService;
    }


    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("Ich war hier");
    }
}
