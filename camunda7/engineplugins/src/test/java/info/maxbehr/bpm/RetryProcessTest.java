package info.maxbehr.bpm;

import info.maxbehr.bpm.delegate.RetryDelegateOne;
import info.maxbehr.bpm.delegate.RetryDelegateTwo;
import org.apache.commons.lang3.time.DateUtils;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.persistence.entity.JobEntity;
import org.camunda.bpm.engine.impl.util.ClockUtil;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class RetryProcessTest {

    protected static final int DEFAULT_RETRIES = 3;

    @Rule
    public ProcessEngineRule processEngineRule = new ProcessEngineRule();

    protected ManagementService managementService;
    protected RuntimeService runtimeService;

    protected Date currentTime;

    @Before
    public void setUp() throws ParseException {
        currentTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2017-01-01T10:00:00");

        managementService = processEngineRule.getManagementService();
        runtimeService = processEngineRule.getRuntimeService();
    }

    @Test
    @Ignore
    @Deployment(resources = { "retrypluginprocess.bpmn" })
    public void shouldUseProfileParseListener() throws ParseException {
        // start the process instance
        ClockUtil.setCurrentTime(currentTime);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("retrypluginprocess");
        String processInstanceId = processInstance.getProcessInstanceId();

        int jobRetries = 0;

        // execution the first service task results with failed job
        jobRetries = executeJob(processInstanceId);
        assertEquals(4, jobRetries);
        // 10 minutes offset as the retry conf. is "R5/PT10M"
        assertDueDate(10);
        RetryDelegateOne.firstAttempt = false;

        // successful execution of the first service task
        jobRetries = executeJob(processInstanceId);
        assertEquals(DEFAULT_RETRIES, jobRetries);

        // successful execution of the second service task
        jobRetries = executeJob(processInstanceId);
        assertEquals(DEFAULT_RETRIES, jobRetries);

        // execution the third service task results with failed job
        jobRetries = executeJob(processInstanceId);
        assertEquals(6, jobRetries);
        // 5 minutes offset as the retry conf. is "R7/PT5M"
        assertDueDate(5);
        RetryDelegateTwo.firstAttempt = false;

        // successful execution of the third service task
        jobRetries = executeJob(processInstanceId);
        assertEquals(DEFAULT_RETRIES, jobRetries);
    }

    protected int executeJob(String processInstanceId) {
        Job job = managementService.createJobQuery().processInstanceId(processInstanceId).singleResult();

        try {
            managementService.executeJob(job.getId());
        } catch (Exception e) {
            // ignore
        }

        job = managementService.createJobQuery().processInstanceId(processInstanceId).singleResult();

        return job.getRetries();
    }

    protected void assertDueDate(int minutesOffset) throws ParseException {
        currentTime = DateUtils.addMinutes(currentTime, minutesOffset);
        Date dueDate = ((JobEntity) managementService.createJobQuery().singleResult()).getDuedate();
        assertEquals(currentTime, dueDate);
        ClockUtil.setCurrentTime(currentTime);
    }
}
