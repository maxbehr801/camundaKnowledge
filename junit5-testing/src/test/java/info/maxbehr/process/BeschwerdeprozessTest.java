package info.maxbehr.process;

import info.maxbehr.process.delegate.BriefErstellenDelegate;
import info.maxbehr.process.listener.AdonisAPIListener;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.extension.process_test_coverage.junit5.ProcessEngineCoverageExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.complete;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.processInstanceQuery;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.runtimeService;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.task;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.taskService;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(ProcessEngineCoverageExtension.class)
@Deployment(resources = {"beschwerdeprozess_mixed.bpmn"})
class BeschwerdeprozessTest {

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
    void shouldExecuteHappyPath() {
        Map<String, Object> startVariables = Map.of("beschwerde", "Happy Path Beschwerde");
        ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(BESCHWERDEPROZESS, startVariables);
        assertThat(processInstance).isActive();
        assertThat(processInstanceQuery().count()).isEqualTo(1);
        assertThat(task(processInstance)).isNotNull();
        assertThat(taskService().createTaskQuery().active().singleResult().getTaskDefinitionKey()).isEqualTo("UT_Beschwerde_erfassen");
        Map<String, Object> beschwerdeErfassungVariables = Map.of(
                "isVorstandsbeschwerde", false,
                "beschwerdeart", "allgemeineBeschwerde");
        complete(task(processInstance), beschwerdeErfassungVariables);
        assertThat(runtimeService().createVariableInstanceQuery().processInstanceIdIn(processInstance.getId()).list()).hasSize(3);
        assertThat(taskService().createTaskQuery().active().singleResult().getTaskDefinitionKey()).isEqualTo("UT_Briefversand_kontrollieren");
        Map<String, Object> kontrollVariables = Map.of(
                "controlexecution_documentation", "happy path documentation",
                "controlexecution_comment", "happy path comment",
                "controlexecution_isApproved", true);
        complete(task(processInstance), kontrollVariables);
        verify(adonisAPIListener, times(1)).notify(any(DelegateTask.class));
        assertThat(processInstance).isEnded();
    }

    @Test
    void shouldExecuteAllgemeineBeschwerde() {
        Map<String, Object> startVariables = Map.of("beschwerde", "Vorstandsbeschwerde Beschwerde");
        ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(BESCHWERDEPROZESS, startVariables);
        assertThat(processInstance).isActive();
        assertThat(processInstanceQuery().count()).isEqualTo(1);
        assertThat(task(processInstance)).isNotNull();
        Map<String, Object> beschwerdeErfassungVariables = Map.of(
                "isVorstandsbeschwerde", true,
                "beschwerdeart", "vorstandsOmbudsmannbeschwerde");
        complete(task(processInstance), beschwerdeErfassungVariables);
        assertThat(task(processInstance)).isNotNull();
        Map<String, Object> freigabeDurchfuehrenVariables = Map.of(
                "controlexecution_documentation", "Dokumentation",
                "controlexecution_comment", "Kommentar",
                "controlexecution_isApproved", true);
        complete(task(processInstance), freigabeDurchfuehrenVariables);
        assertThat(task(processInstance)).isNotNull();
        Map<String, Object> freigabeKontrollierenVariables = Map.of(
                "controlexecution_documentation", "Dokumentation F1",
                "controlexecution_comment", "Kommentar F1",
                "controlexecution_isApproved", true);
        complete(task(processInstance), freigabeKontrollierenVariables);
        assertThat(task(processInstance)).isNotNull();
        Map<String, Object> leiterBeschwerdemanagementVariables = Map.of(
                "controlexecution_documentation", "Dokumentation LBM",
                "controlexecution_comment", "Kommentar LBM",
                "controlexecution_isApproved", true);
        complete(task(processInstance), leiterBeschwerdemanagementVariables);
        assertThat(processInstance).isEnded();
    }

    @Test
    void shouldExecuteBaFinBeschwerde() {
        Map<String, Object> startVariables = Map.of("beschwerde", "BaFin Beschwerde");
        ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(BESCHWERDEPROZESS, startVariables);
        assertThat(processInstance).isActive();
        assertThat(processInstanceQuery().count()).isEqualTo(1);
        assertThat(task(processInstance)).isNotNull();
        Map<String, Object> beschwerdeErfassungVariables = Map.of(
                "isVorstandsbeschwerde", true,
                "beschwerdeart", "bafinBeschwerde");
        complete(task(processInstance), beschwerdeErfassungVariables);
        assertThat(task(processInstance)).isNotNull();
        complete(task(processInstance));
        assertThat(task(processInstance)).isNotNull();
        Map<String, Object> stellungnahmeFreigebenVariables = Map.of(
                "controlexecution_documentation", "Dokumentation Vorstandsvorsitzender",
                "controlexecution_comment", "Kommentar Vorstandsvorsitzender",
                "controlexecution_isApproved", true);
        complete(task(processInstance), stellungnahmeFreigebenVariables);
        assertThat(task(processInstance)).isNotNull();
        complete(task(processInstance));
        Map<String, Object> leiterBeschwerdemanagementVariables = Map.of(
                "controlexecution_documentation", "Dokumentation LBM",
                "controlexecution_comment", "Kommentar LBM",
                "controlexecution_isApproved", true);
        complete(task(processInstance), leiterBeschwerdemanagementVariables);
        assertThat(processInstance).isEnded();
    }

    @Test
    void shouldExecuteBeschwerdeAnVorstandsvorsitzenden() {
        Map<String, Object> startVariables = Map.of("beschwerde", "Vorstandsvorsitzender Beschwerde");
        ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(BESCHWERDEPROZESS, startVariables);
        assertThat(processInstance).isActive();
        assertThat(processInstanceQuery().count()).isEqualTo(1);
        assertThat(task(processInstance)).isNotNull();
        Map<String, Object> beschwerdeErfassungVariables = Map.of(
                "isVorstandsbeschwerde", true,
                "beschwerdeart", "beschwerdeAnVorstandsvorsitzenden");
        complete(task(processInstance), beschwerdeErfassungVariables);
        assertThat(task(processInstance)).isNotNull();
        Map<String, Object> leiterBeschwerdemanagementVariables = Map.of(
                "controlexecution_documentation", "Dokumentation LBM",
                "controlexecution_comment", "Kommentar LBM",
                "controlexecution_isApproved", true);
        complete(task(processInstance), leiterBeschwerdemanagementVariables);
        assertThat(processInstance).isEnded();
    }

    @Test
    void shouldExecuteEskalationsBeschwerdeNichtFreigegeben() {
        Map<String, Object> startVariables = Map.of("beschwerde", "Eskalationsbeschwerde");
        ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(BESCHWERDEPROZESS, startVariables);
        assertThat(processInstance).isActive();
        assertThat(processInstanceQuery().count()).isEqualTo(1);
        assertThat(task(processInstance)).isNotNull();
        Map<String, Object> beschwerdeErfassungVariables = Map.of(
                "isVorstandsbeschwerde", true,
                "beschwerdeart", "aufsichtsrats_vorstandsbeschwerde_eskalation");
        complete(task(processInstance), beschwerdeErfassungVariables);
        assertThat(task(processInstance)).isNotNull();
        Map<String, Object> briefvorlagePruefenUndFreigebenVariables = Map.of(
                "isBriefvorlageFreigegeben", false,
                "controlexecution_documentation", "Dokumentation Vorstandsvorsitzender",
                "controlexecution_comment", "Kommentar Vorstandsvorsitzender",
                "controlexecution_isApproved", false);
        complete(task(processInstance), briefvorlagePruefenUndFreigebenVariables);
        assertThat(task(processInstance)).isNotNull();
        assertThat(taskService().createTaskQuery().active().singleResult().getTaskDefinitionKey()).isEqualTo("UT_Briefvorlage_pruefen_und_freigeben");
        Map<String, Object> briefvorlagePruefenUndFreigebenTrueVariables = Map.of(
                "isBriefvorlageFreigegeben", true,
                "controlexecution_documentation", "Dokumentation Vorstandsvorsitzender",
                "controlexecution_comment", "Kommentar Vorstandsvorsitzender",
                "controlexecution_isApproved", true);
        complete(task(processInstance), briefvorlagePruefenUndFreigebenTrueVariables);
    }
}
