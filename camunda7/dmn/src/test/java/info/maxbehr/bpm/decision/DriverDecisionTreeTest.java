package info.maxbehr.bpm.decision;

import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

class DriverDecisionTreeTest {

    @Test
    void testDriverDecisionTree() {
        DmnEngine dmnEngine = DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("driver_decision_tree.dmn");

        List<DmnDecision> dmnDecisions = dmnEngine.parseDecisions(inputStream);

        DmnDecision dmnDecisionMoreThan65 = dmnDecisions.stream().filter(dmnDecision -> dmnDecision.getKey().equals("Driver_Decision_Tree_more_65_Decision")).findFirst().orElseThrow(NoSuchElementException::new);
        DmnDecision dmnDecisionLessThan65 = dmnDecisions.stream().filter(dmnDecision -> dmnDecision.getKey().equals("Driver_Decision_Tree_less_65_Decision")).findFirst().orElseThrow(NoSuchElementException::new);

        VariableMap variables = Variables.createVariables()
                .putValue("yards", 70)
                .putValue("fairwayLessThan40yards", true)
                .putValue("woodRemovesHazard", false)
                .putValue("nextClubRemoveHazard", false)
                .putValue("canCarryHazardWithoutTrouble", false)
                .putValue("woodInHazard", true);

        DmnDecisionTableResult dmnDecisionRuleResultsMoreThan65 = dmnEngine.evaluateDecisionTable(dmnDecisionMoreThan65, variables);
        DmnDecisionTableResult dmnDecisionRuleResultsLessThan65 = dmnEngine.evaluateDecisionTable(dmnDecisionLessThan65, variables);

        assertThat(dmnDecisionRuleResultsMoreThan65).hasSize(1);
        assertThat(dmnDecisionRuleResultsLessThan65).hasSize(1);
        System.out.println(">65: " + dmnDecisionRuleResultsMoreThan65);
        System.out.println("<65: " + dmnDecisionRuleResultsLessThan65);
    }
}
