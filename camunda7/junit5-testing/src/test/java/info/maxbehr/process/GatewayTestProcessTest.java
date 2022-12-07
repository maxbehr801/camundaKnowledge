package info.maxbehr.process;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.extension.process_test_coverage.junit5.ProcessEngineCoverageExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.complete;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.runtimeService;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.task;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.taskService;

@ExtendWith(ProcessEngineCoverageExtension.class)
@Deployment(resources = {"gatewaytest.bpmn"})
public class GatewayTestProcessTest {

    static final String BESCHWERDEPROZESS = "Gatewaytest";
    static final String UT_OBEN_ODER_UNTEN = "UT_Oben_oder_unten";
    static final String VARIABLE_ENTSCHEIDUNG = "entscheidung";
    static final String END_EVENT_OBEN = "EndEvent_oben";
    static final String END_EVENT_UNTEN = "EndEvent_unten";

    @Test
    void shouldExecutePathOben() {
        ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(BESCHWERDEPROZESS);
        assertThat(taskService().createTaskQuery().active().singleResult().getTaskDefinitionKey()).isEqualTo(UT_OBEN_ODER_UNTEN);
        Map<String, Object> processVariables = Map.of(
                VARIABLE_ENTSCHEIDUNG, "oben");
        complete(task(processInstance), processVariables);
        assertThat(processInstance).hasPassed(END_EVENT_OBEN).isEnded();
    }

    @Test
    void shouldExecutePathUnten() {
        ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(BESCHWERDEPROZESS);
        assertThat(taskService().createTaskQuery().active().singleResult().getTaskDefinitionKey()).isEqualTo(UT_OBEN_ODER_UNTEN);
        Map<String, Object> processVariables = Map.of(
                VARIABLE_ENTSCHEIDUNG, "unten");
        complete(task(processInstance), processVariables);
        assertThat(processInstance).hasPassed(END_EVENT_UNTEN).isEnded();
    }
}
