package info.maxbehr.bpm.plugin;

import info.maxbehr.bpm.listener.ProgressLoggingSupportParseListener;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProgressLoggingSupportParseListenerPlugin extends AbstractProcessEnginePlugin {

    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        // get all existing preParseListeners
        List<BpmnParseListener> preParseListeners = processEngineConfiguration.getCustomPreBPMNParseListeners();

        if (preParseListeners == null) {
            // if no preParseListener exists, create a new list
            preParseListeners = new ArrayList<>();
            processEngineConfiguration.setCustomPreBPMNParseListeners(preParseListeners);
        }
        // add new BPMN Parse Listener
        preParseListeners.add(new ProgressLoggingSupportParseListener());
    }
}
