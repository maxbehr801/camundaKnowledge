package info.maxbehr.bpm.command;

import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;

public class EndBlockingCommand implements Command<Void> {

    @Override
    public Void execute(CommandContext commandContext) {
        return null;
    }
}
