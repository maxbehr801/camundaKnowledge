package info.maxbehr.bpm.listener;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.identity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VierAugenPrinzipListener implements TaskListener {

    private static final Logger log = LoggerFactory.getLogger(VierAugenPrinzipListener.class);

    private final IdentityService identityService;
    private final TaskService taskService;

    public VierAugenPrinzipListener(IdentityService identityService, TaskService taskService) {
        this.identityService = identityService;
        this.taskService = taskService;
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("Task wurde von {} bearbeitet.", delegateTask.getAssignee());

        String candidateGroupId = delegateTask.getCandidates().stream().findFirst().get().getGroupId();
        List<User> listOfUsers = identityService.createUserQuery().memberOfGroup(candidateGroupId).list();

        List<String> candidateUser = listOfUsers.stream()
                .map(User::getId)
                .filter(id -> !(id.equals(delegateTask.getAssignee())))
                .collect(Collectors.toList());

        log.info("Vier-Augen-Prinzip kann damit nur von {} ausgeführt werden.", candidateUser);

        delegateTask.setVariable("candidateUsers", candidateUser);
    }
}
