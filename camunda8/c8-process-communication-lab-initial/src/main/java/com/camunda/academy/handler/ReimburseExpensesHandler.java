package com.camunda.academy.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

import java.util.Map;

public class ReimburseExpensesHandler implements JobHandler{
	
	    @Override
	    public void handle(JobClient client, ActivatedJob job) throws Exception {
	    	
	    	final Map<String, Object> inputVariables = job.getVariablesAsMap();
	    	final String travelRequestId = (String) inputVariables.get("travelRequestId");	    	
	    	
		    System.out.println(travelRequestId + " Reimbursement Request: Expenses reimbursed");	
		    
		    //Complete the Job
		    client.newCompleteCommand(job.getKey()).send().join();
			
	    }

}
