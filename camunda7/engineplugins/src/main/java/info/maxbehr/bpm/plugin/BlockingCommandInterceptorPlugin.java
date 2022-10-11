package info.maxbehr.bpm.plugin;

import info.maxbehr.bpm.interceptor.BlockingCommandInterceptor;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.interceptor.CommandInterceptor;
import org.springframework.cglib.core.Block;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BlockingCommandInterceptorPlugin extends AbstractProcessEnginePlugin {

    protected BlockingCommandInterceptor blockingCommandInterceptor = new BlockingCommandInterceptor();

    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        // add command interceptor to configuration
        List<CommandInterceptor> customPreCommandInterceptorsTxRequired =
                processEngineConfiguration.getCustomPreCommandInterceptorsTxRequired();
        if (customPreCommandInterceptorsTxRequired == null) {
            customPreCommandInterceptorsTxRequired = new ArrayList<>();
            processEngineConfiguration.setCustomPreCommandInterceptorsTxRequired(customPreCommandInterceptorsTxRequired);
        }
        customPreCommandInterceptorsTxRequired.add(blockingCommandInterceptor);

        List<CommandInterceptor> customPreCommandInterceptorsTxRequiresNew =
                processEngineConfiguration.getCustomPreCommandInterceptorsTxRequiresNew();
        if (customPreCommandInterceptorsTxRequiresNew == null) {
            customPreCommandInterceptorsTxRequiresNew = new ArrayList<>();
            processEngineConfiguration.setCustomPreCommandInterceptorsTxRequiresNew(customPreCommandInterceptorsTxRequiresNew);
        }
        customPreCommandInterceptorsTxRequiresNew.add(blockingCommandInterceptor);
    }

    @Override
    public void postProcessEngineBuild(ProcessEngine processEngine) {
        // after the process engine is built, start blocking
        blockingCommandInterceptor.startBlocking();
    }

    public BlockingCommandInterceptor getBlockingCommandIntercpetor() {
        return blockingCommandInterceptor;
    }
}
