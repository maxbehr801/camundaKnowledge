package info.maxbehr.bpm.plugin;

import info.maxbehr.bpm.listener.FailedJobRetryProfileParseListener;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class FailedJobRetryProfilePlugin extends AbstractProcessEnginePlugin {

    private Map<String, String> retryProfiles;

    public Map<String, String> getRetryProfiles() {
        return retryProfiles;
    }

    public void setRetryProfiles(Map<String, String> retryProfiles) {
        this.retryProfiles = retryProfiles;
    }

    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        List<BpmnParseListener> customPreParseListeners = processEngineConfiguration.getCustomPreBPMNParseListeners();
        if (customPreParseListeners == null) {
            customPreParseListeners = new ArrayList<>();
            processEngineConfiguration.setCustomPreBPMNParseListeners(customPreParseListeners);
        }
        FailedJobRetryProfileParseListener failedJobRetryProfileParseListener = new FailedJobRetryProfileParseListener(retryProfiles);
        customPreParseListeners.add(failedJobRetryProfileParseListener);
    }
}
