package info.maxbehr.process.camunda8.worker;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Component
@Slf4j
public class PremiumShippingWorker {

    private final ZeebeClient client;

    public PremiumShippingWorker(ZeebeClient client) {
        this.client = client;
    }

    @JobWorker(type = "premiumshipping")
    public Map<String, Object> handlePremiumShipping(final ActivatedJob job) {
        log.info("job: {}", job);
        log.info("variables: {}", job.getVariables());
        return Map.of("activity", "premiumshipping");
    }

    @JobWorker(type = "defaultshipping")
    public Map<String, Object> handleDefaultShipping(final ActivatedJob job) {
        log.info("job: {}", job);
        log.info("variables: {}", job.getVariables());

        return Map.of("activity", "defaultshipping");
    }

    @JobWorker(type = "complete", autoComplete = false)
    public void complete(final ActivatedJob job) {
        log.info("ich bin da {}", job);
        log.info(String.valueOf(job.getVariablesAsMap().containsKey("meineVariable")));
        client.newSetVariablesCommand(job.getElementInstanceKey()).variables(Map.of("meineVariable", "mein Wert")).send().join();
        client.newFailCommand(job).retries(1).retryBackoff(Duration.of(10000, ChronoUnit.MILLIS)).errorMessage("this is an error").send().join();
    }

    @JobWorker(type = "informcustomer")
    public Map<String, Object> handleInformCustomer(final ActivatedJob job) {
        log.info("job: {}", job);
        log.info("variables: {}", job.getVariables());
        return Map.of("result", "you have been rejected");
    }
}
