package info.maxbehr.bpm;

import info.maxbehr.bpm.handler.OrderHandler;
import info.maxbehr.bpm.handler.PackItemsHandler;
import info.maxbehr.bpm.handler.ProcessPaymentHandler;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;

import java.util.Scanner;

public class OrderApplication {

	// Zeebe Client Credentials
    private static final String ZEEBE_PROPERTIES_PATH = "src/main/resources/application.properties";
    private static String ZEEBE_ADDRESS;
    private static String ZEEBE_CLIENT_ID;
    private static String ZEEBE_CLIENT_SECRET;
    private static String ZEEBE_TOKEN_AUDIENCE = "zeebe.camunda.io";  

	// Process instance creation
	private static final String PROCESS_ID = "orderProcess";	
	private static final int NUM_INSTANCES = 1; // TOTAL NUMBER OF NEW PROCESS INSTANCES CREATED

	// Worker configuration
	private static final int WORKER_TIMEOUT = 1; // Set the time for how long a job is exclusively assigned for this worker.
		
	public static void main(String[] args) {
        loadProperties();
		final OAuthCredentialsProvider credentialsProvider = new OAuthCredentialsProviderBuilder()
			.audience(ZEEBE_TOKEN_AUDIENCE)
			.clientId(ZEEBE_CLIENT_ID)
			.clientSecret(ZEEBE_CLIENT_SECRET)
			.build();

		try (final ZeebeClient client = ZeebeClient.newClientBuilder()		
		     	.gatewayAddress(ZEEBE_ADDRESS)
		     	.credentialsProvider(credentialsProvider)			
		     	.build()) {

			// Process Instance creator looper
			startProcessInstances(client, NUM_INSTANCES);

			final JobWorker OrderWorker = client.newWorker()
				.jobType("trackOrderStatus")
				.handler(new OrderHandler())
				.open();
			
			final JobWorker ProcessPaymentWorker = client.newWorker()
				.jobType("processPayment")
				.handler(new ProcessPaymentHandler())				
                .open();
		
			final JobWorker PackItemsWorker = client.newWorker()
				.jobType("packItems")
				.handler(new PackItemsHandler())				
				.open();
 			
			// Terminate the worker with an Integer input
			Scanner sc = new Scanner(System.in);			
			sc.nextInt();
			sc.close();
			OrderWorker.close();
			ProcessPaymentWorker.close();
			PackItemsWorker.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void startProcessInstances(ZeebeClient zeebeClient, int numInstances) {
		
		System.out.println(PROCESS_ID + ": Starting "+ numInstances + " process instances...");
		for (int i = 0; i < numInstances; i++) {
				
			FakeRandomizer fakeRequest = new FakeRandomizer();	

			zeebeClient.newCreateInstanceCommand()
				.bpmnProcessId(PROCESS_ID)			
				.latestVersion()
				.variables(fakeRequest.getRandom())
				.send()				
				.join();
		}
		System.out.println("... " + numInstances + " instances created");
	}

	private static void loadProperties() {
		ZEEBE_ADDRESS = "xxx";
		ZEEBE_CLIENT_ID = "xxx";
		ZEEBE_CLIENT_SECRET = "xxx";
	}
}
