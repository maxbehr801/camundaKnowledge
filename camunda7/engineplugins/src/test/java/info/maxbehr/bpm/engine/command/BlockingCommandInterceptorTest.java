package info.maxbehr.bpm.engine.command;

import info.maxbehr.bpm.command.EndBlockingCommand;
import info.maxbehr.bpm.interceptor.BlockedCommandException;
import info.maxbehr.bpm.plugin.BlockingCommandInterceptorPlugin;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.spring.boot.starter.test.helper.StandaloneInMemoryTestConfiguration;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BlockingCommandInterceptorTest {

    protected static ProcessEngine processEngine;

    @BeforeAll
    static void createProcessEngine() {
        StandaloneInMemoryTestConfiguration configuration = new StandaloneInMemoryTestConfiguration();
        configuration.getProcessEnginePlugins().add(new BlockingCommandInterceptorPlugin());
        processEngine = configuration.buildProcessEngine();
    }

    @Test
    void testBlockingInterceptor() {
        // initially the engine blocks all commands
        try {
            processEngine.getRepositoryService().createDeploymentQuery().list();
            Assert.fail("Exception expected");
        } catch (BlockedCommandException e) {
            // expected
        }

        // stop blocking commands
        ((ProcessEngineConfigurationImpl) processEngine.getProcessEngineConfiguration()).getCommandExecutorTxRequired().execute(new EndBlockingCommand());

        // now we can execute the query
        try {
            Assert.assertEquals(0, processEngine.getRepositoryService().createDeploymentQuery().count());
        } catch (BlockedCommandException e) {
            Assert.fail("Did not expect an exception");
        }
    }
}
