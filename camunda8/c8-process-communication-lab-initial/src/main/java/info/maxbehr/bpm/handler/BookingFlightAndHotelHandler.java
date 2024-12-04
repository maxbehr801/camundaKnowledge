package info.maxbehr.bpm.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

import java.util.Map;

public class BookingFlightAndHotelHandler implements JobHandler{
	
	    @Override
	    public void handle(JobClient client, ActivatedJob job) throws Exception {
	    	
	    	final Map<String, Object> inputVariables = job.getVariablesAsMap();
	    	final String travelRequestId = (String) inputVariables.get("travelRequestId");	    	
	    	
		    System.out.println(travelRequestId + " Travel Request: Flight and Hotel booked");	
		    
		    //Complete the Job
		    client.newCompleteCommand(job.getKey()).send().join();
			
	    }

}
