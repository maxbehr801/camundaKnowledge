package info.maxbehr.bpm.interceptor;

import org.camunda.bpm.engine.ProcessEngineException;

public class BlockedCommandException extends ProcessEngineException {


    private static final long serialVersionUID = -6964960631245720765L;

    public BlockedCommandException() {
        super();
    }

    public BlockedCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlockedCommandException(String message) {
        super(message);
    }

    public BlockedCommandException(Throwable cause) {
        super(cause);
    }
}
