package info.maxbehr.bpm.interceptor;

import info.maxbehr.bpm.command.EndBlockingCommand;
import info.maxbehr.bpm.command.StartBlockingCommand;
import info.maxbehr.bpm.command.UnblockedCommand;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

public class BlockingCommandInterceptor extends CommandInterceptor {

    private static final Logger log = LoggerFactory.getLogger(BlockingCommandInterceptor.class);

    protected AtomicBoolean shouldBlock = new AtomicBoolean();

    @Override
    public <T> T execute(Command<T> command) {
        // handle start blocking
        if (command instanceof StartBlockingCommand) {
            startBlocking();
            return null;
        }

        // handle endblocking commands
        if (command instanceof EndBlockingCommand) {
            stopBlocking();
            return null;
        }

        // handle unblocked commands
        if (command instanceof UnblockedCommand) {
            return next.execute(command);
        } else {
            // otherwise, check whether we should block this
            if (shouldBlock.get()) {
                throw new BlockedCommandException("Process Engine blocked");
            } else {
                return next.execute(command);
            }
        }
    }

    public void startBlocking() {
        shouldBlock.set(true);
        log.info("Start blocking commands");
    }

    public void stopBlocking() {
        shouldBlock.set(false);
        log.info("end blocking commands");
    }
}
