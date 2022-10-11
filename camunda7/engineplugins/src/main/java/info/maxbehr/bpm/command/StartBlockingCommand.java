package info.maxbehr.bpm.command;

import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;

public class StartBlockingCommand implements Command<Void> {

    @Override
    public Void execute(CommandContext commandContext) {
        return null;
    }
}
