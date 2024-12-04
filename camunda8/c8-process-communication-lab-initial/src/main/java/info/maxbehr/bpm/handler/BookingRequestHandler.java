package info.maxbehr.bpm.handler;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BookingRequestHandler implements JobHandler {

    private static final String MESSAGE_NAME = "msg_startBookingRequest";

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        final Map<String, Object> inputVariables = job.getVariablesAsMap();
        final var travelRequestId = (String) inputVariables.get("travelRequestId");
        final var travelDestination = (String) inputVariables.get("travelDestination");
        final var travelDate = (String) inputVariables.get("travelDate");
        final var travelFlight = (String) inputVariables.get("travelFlight");
        final var travelHotel = (String) inputVariables.get("travelHotel");

        var properties = loadProperties("src/main/resources/travelagency.cluster.properties");

        final var ZEEBE_ADDRESS = properties.getProperty("ZEEBE_ADDRESS");
        final var ZEEBE_CLIENT_ID = properties.getProperty("ZEEBE_CLIENT_ID");
        final var ZEEBE_CLIENT_SECRET = properties.getProperty("ZEEBE_CLIENT_SECRET");
        final var ZEEBE_AUTHORIZATION_SERVER_URL = properties.getProperty("ZEEBE_AUTHORIZATION_SERVER_URL");
        final var ZEEBE_TOKEN_AUDIENCE = properties.getProperty("ZEEBE_TOKEN_AUDIENCE");

        final Map<String, Object> messageVariables = new HashMap<String, Object>();

        messageVariables.put("travelRequestId", travelRequestId);
        messageVariables.put("travelDestination", travelDestination);
        messageVariables.put("travelDate", travelDate);
        messageVariables.put("travelFlight", travelFlight);
        messageVariables.put("travelHotel", travelHotel);

        final OAuthCredentialsProvider credentialsProvider =
                new OAuthCredentialsProviderBuilder()
                        .authorizationServerUrl(ZEEBE_AUTHORIZATION_SERVER_URL)
                        .audience(ZEEBE_TOKEN_AUDIENCE)
                        .clientId(ZEEBE_CLIENT_ID)
                        .clientSecret(ZEEBE_CLIENT_SECRET)
                        .build();

        try (final ZeebeClient travelAgencyClient = ZeebeClient.newClientBuilder()
                .gatewayAddress(ZEEBE_ADDRESS)
                .credentialsProvider(credentialsProvider)
                .build()) {

            travelAgencyClient.newPublishMessageCommand()
                    .messageName(MESSAGE_NAME)
                    .correlationKey(travelRequestId)
                    .variables(messageVariables)
                    .send()
                    .join();
            System.out.println(travelRequestId + " Travel Request started");
            client.newCompleteCommand(job.getKey()).send().join();
        }
    }

    public static Properties loadProperties(String fileName) throws IOException {
        InputStream input = new FileInputStream(fileName);
        Properties prop = new Properties();
        prop.load(input);
        return prop;
    }

}
