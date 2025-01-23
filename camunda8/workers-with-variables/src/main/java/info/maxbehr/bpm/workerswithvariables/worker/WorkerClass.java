package info.maxbehr.bpm.workerswithvariables.worker;

import info.maxbehr.bpm.workerswithvariables.worker.model.MyProcessVariables;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import io.camunda.zeebe.spring.client.annotation.VariablesAsType;
import org.springframework.stereotype.Component;

@Component
public class WorkerClass {

    private final ZeebeClient zeebeClient;

    public WorkerClass(ZeebeClient zeebeClient) {
        this.zeebeClient = zeebeClient;
    }

    @JobWorker(type = "worker-with-mapping")
    public void workerWithMapping(final JobClient jobClient, final ActivatedJob activatedJob, @Variable(name = "vorname") String vorname, @VariablesAsType MyProcessVariables variables) {
        System.out.println("@Variable " + vorname);
        System.out.println("@VariablesAsType " + variables);
        System.out.println("über job " + activatedJob.getVariables());
        jobClient.newCompleteCommand(activatedJob.getKey())
                .variable("output", vorname.toUpperCase())
                .send()
                .join();
    }

    @JobWorker(type = "worker-without-mapping")
    public void workerWithoutMapping(final JobClient jobClient, final ActivatedJob activatedJob) {
        var upperCase = activatedJob.getVariable("nachname").toString().toUpperCase();
        jobClient.newCompleteCommand(activatedJob.getKey())
                .variable("nachname", upperCase)
                .send()
                .join();
    }
}
