package info.maxbehr.process;

import info.maxbehr.process.delegate.BriefErstellenDelegate;
import info.maxbehr.process.listener.AdonisAPIListener;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.extension.process_test_coverage.junit5.ProcessEngineCoverageExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;

@ExtendWith(ProcessEngineCoverageExtension.class)
@Deployment(resources = {"beschwerdeprozess_mixed.bpmn"})
public class MittendrinProzessTest {

    static final String BESCHWERDEPROZESS = "beschwerdebearbeitung";

    @Mock
    private BriefErstellenDelegate briefErstellenDelegate;
    @Mock
    private AdonisAPIListener adonisAPIListener;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        Mocks.register("briefErstellenDelegate", briefErstellenDelegate);
        Mocks.register("adonisAPIListener", adonisAPIListener);
    }

    @Test
    void testControlExecutionUserTask() {
        ProcessInstance processInstance = runtimeService()
                .createProcessInstanceByKey(BESCHWERDEPROZESS)
                .startBeforeActivity("UT_Briefversand_kontrollieren")
                .setVariable("beschwerde", "Beschwerde")
                .execute();
        Map<String, Object> controlExecutionVariables = Map.of(
                "documentation", "documentation",
                "comment", "comment",
                "approved", true);
        taskService().setAssignee(task(processInstance).getId(), "demo");
        complete(task(processInstance), controlExecutionVariables);
        List<String> tasks = taskService()
                .createTaskQuery()
                .active()
                .list().stream().map(Task::getTaskDefinitionKey).collect(Collectors.toList());
        assertThat(processInstance).isEnded();
    }
}
