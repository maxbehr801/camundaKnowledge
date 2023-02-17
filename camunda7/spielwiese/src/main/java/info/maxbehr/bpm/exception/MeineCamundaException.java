package info.maxbehr.bpm.exception;

public class MeineCamundaException extends RuntimeException {

    private String message;

    public MeineCamundaException(String message) {
        super(message);
    }
}
