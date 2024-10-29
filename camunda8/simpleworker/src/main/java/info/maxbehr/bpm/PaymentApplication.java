package info.maxbehr.bpm;

import info.maxbehr.bpm.handler.CreditCardServiceHandler;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class PaymentApplication {

    private static final String CAMUNDA_ADDRESS = "xyz.bru-2.zeebe.camunda.io:443";
    private static final String CAMUNDA_CLIENT_ID = "xyz";
    private static final String CAMUNDA_CLIENT_SECRET = "xyz";
    private static final String CAMUNDA_AUTHORIZATION_SERVER_URL = "https://login.cloud.camunda.io/oauth/token";
    private static final String CAMUNDA_TOKEN_AUDIENCE = "zeebe.camunda.io";

    public static void main(String[] args) {
        final OAuthCredentialsProvider credentialsProvider =
                new OAuthCredentialsProviderBuilder()
                        .authorizationServerUrl(CAMUNDA_AUTHORIZATION_SERVER_URL)
                        .audience(CAMUNDA_TOKEN_AUDIENCE)
                        .clientId(CAMUNDA_CLIENT_ID)
                        .clientSecret(CAMUNDA_CLIENT_SECRET)
                        .build();

        try (final ZeebeClient client =
                     ZeebeClient.newClientBuilder()
                             .gatewayAddress(CAMUNDA_ADDRESS)
                             .credentialsProvider(credentialsProvider)
                             .build()) {

            final Map<String, Object> variables = new HashMap<>();
            variables.put("reference", "C8_12345");
            variables.put("amount", Double.valueOf(100.00));
            variables.put("cardNumber", "1234567812345678");
            variables.put("cardExpiry", "12/2023");
            variables.put("cardCVC", "123");

            client.newCreateInstanceCommand()
                    .bpmnProcessId("paymentProcess")
                    .latestVersion()
                    .variables(variables)
                    .send()
                    .join();

            final JobWorker creditCardWorker =
                    client.newWorker()
                            .jobType("chargeCreditCard")
                            .handler(new CreditCardServiceHandler())
                            .timeout(Duration.ofSeconds(10).toMillis())
                            .open();
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
