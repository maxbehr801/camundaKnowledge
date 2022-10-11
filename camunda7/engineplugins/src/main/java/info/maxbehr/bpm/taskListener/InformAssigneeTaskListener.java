package info.maxbehr.bpm.taskListener;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class InformAssigneeTaskListener implements TaskListener {

    private static final Logger log = LoggerFactory.getLogger(InformAssigneeTaskListener.class);

    public static List<String> assigneeList = new ArrayList<>();

    private static InformAssigneeTaskListener instance = null;

    protected InformAssigneeTaskListener() {}

    public static InformAssigneeTaskListener getInstance() {
        if (instance == null) {
            instance = new InformAssigneeTaskListener();
        }
        return instance;
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        String assignee = delegateTask.getAssignee();
        assigneeList.add(assignee);
        log.info("Hello {}! Please start to work on your task {}", assignee, delegateTask.getName());
    }
}
