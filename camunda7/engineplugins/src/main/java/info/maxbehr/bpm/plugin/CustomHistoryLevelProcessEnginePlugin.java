package info.maxbehr.bpm.plugin;

import info.maxbehr.bpm.historylevel.CustomVariableHistoryLevel;
import info.maxbehr.bpm.historylevel.PerProcessHistoryLevel;
import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.history.HistoryLevel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomHistoryLevelProcessEnginePlugin extends AbstractProcessEnginePlugin {

    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        List<HistoryLevel> customHistoryLevels = processEngineConfiguration.getCustomHistoryLevels();
        if (customHistoryLevels == null) {
            customHistoryLevels = new ArrayList<>();
            processEngineConfiguration.setCustomHistoryLevels(customHistoryLevels);
        }
        customHistoryLevels.add(CustomVariableHistoryLevel.getInstance());
        customHistoryLevels.add(PerProcessHistoryLevel.getInstance());
    }

    @Override
    public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        PerProcessHistoryLevel.getInstance().addHistoryLevels(processEngineConfiguration.getCustomHistoryLevels());
    }
}
