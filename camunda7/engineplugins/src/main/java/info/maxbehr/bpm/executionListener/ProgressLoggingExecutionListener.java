package info.maxbehr.bpm.executionListener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ProgressLoggingExecutionListener implements ExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(ProgressLoggingExecutionListener.class);

    public static List<String> progressValueList = new ArrayList<>();

    private String propertyValue;

    public ProgressLoggingExecutionListener(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        progressValueList.add(propertyValue);
        log.info("value of service task extension property 'progress': {}", propertyValue);
    }
}
