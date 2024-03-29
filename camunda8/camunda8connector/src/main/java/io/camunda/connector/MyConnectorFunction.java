package io.camunda.connector;

import io.camunda.connector.api.annotation.OutboundConnector;
import io.camunda.connector.api.outbound.OutboundConnectorContext;
import io.camunda.connector.api.outbound.OutboundConnectorFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OutboundConnector(
        name = "MYCONNECTOR",
        inputVariables = {"myProperty", "authentication"},
        type = "io.camunda:my-connector:1")
public class MyConnectorFunction implements OutboundConnectorFunction {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyConnectorFunction.class);

    @Override
    public Object execute(OutboundConnectorContext context) throws Exception {
        var connectorRequest = context.getVariablesAsType(MyConnectorRequest.class);

        context.validate(connectorRequest);
        context.replaceSecrets(connectorRequest);

        return executeConnector(connectorRequest);

    }

    private MyConnectorResult executeConnector(final MyConnectorRequest connectorRequest) {
        // TODO: implement connector logic
        LOGGER.info("Executing my connector with request {}", connectorRequest);
        var result = new MyConnectorResult();
        result.setMyProperty("Message received: " + connectorRequest.getMessage());
        return result;
    }
}
