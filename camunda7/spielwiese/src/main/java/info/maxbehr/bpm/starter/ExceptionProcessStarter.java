package info.maxbehr.bpm.starter;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exceptionprocess")
public class ExceptionProcessStarter {

    private static final Logger log = LoggerFactory.getLogger(ExceptionProcessStarter.class);
    private static final String PROCESS_KEY = "ExceptionProcess";

    private final RuntimeService runtimeService;
    private final TaskService taskService;

    public ExceptionProcessStarter(RuntimeService runtimeService, TaskService taskService) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
    }

    @GetMapping("/start")
    public String startProcess() {
        return runtimeService.startProcessInstanceByKey(PROCESS_KEY).getRootProcessInstanceId();
    }

    @GetMapping("/task/{pid}")
    public void startTask(@PathVariable String pid) {
        Task task = taskService.createTaskQuery().processInstanceId(pid).singleResult();
        try {
            taskService.setAssignee(task.getId(), "demo");
        } catch (Exception e) {
            log.info("Fehler beim Setzen des Assignee");
        }
    }
}
