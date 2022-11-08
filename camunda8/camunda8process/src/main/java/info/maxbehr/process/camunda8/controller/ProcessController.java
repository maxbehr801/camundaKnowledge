package info.maxbehr.process.camunda8.controller;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.ZeebeFuture;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/process")
@Slf4j
public class ProcessController {

    public static final String PROCESSID = "customer_process";

    private final ZeebeClient zeebe;

    public ProcessController(ZeebeClient client) {
        this.zeebe = client;
    }

    @GetMapping("/start")
    public long startProcessInstance() {
        log.info("Starting process");
        ProcessInstanceEvent processInstanceEvent = zeebe.newCreateInstanceCommand()
                .bpmnProcessId(PROCESSID)
                .latestVersion()
                .send()
                .join();
        return processInstanceEvent.getProcessInstanceKey();
    }
}
