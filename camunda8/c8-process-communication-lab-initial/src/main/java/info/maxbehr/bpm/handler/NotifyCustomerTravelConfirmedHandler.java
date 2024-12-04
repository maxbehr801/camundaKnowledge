package info.maxbehr.bpm.handler;

import com.camunda.academy.service.CustomerService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

import java.util.Map;

public class NotifyCustomerTravelConfirmedHandler implements JobHandler {
	
	//Create a Customer Service for Testing
    CustomerService customerService = new CustomerService();

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
    	
    	final Map<String, Object> inputVariables = job.getVariablesAsMap();
    	final String travelRequestId = (String) inputVariables.get("travelRequestId");	
    	
    	//Notify customer
    	customerService.notifyTravelConfirmed(travelRequestId);
           	
    	//Complete the Job
    	client.newCompleteCommand(job.getKey()).send().join();
    }
}