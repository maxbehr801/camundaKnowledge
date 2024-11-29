package info.maxbehr.bpm.connector;

import info.maxbehr.bpm.connector.dto.ConcatenationConnectorRequest;
import io.camunda.connector.api.error.ConnectorInputException;
import io.camunda.connector.test.outbound.OutboundConnectorContextBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConcatenationRequestTest {

    private String input1;
    private String input2;

    @Test
    void shouldFailWhenValidate_NoInput1() {
        var input = new ConcatenationConnectorRequest(input1, input2);

        var context = OutboundConnectorContextBuilder
                .create()
                .variables(input).build();

        assertThatThrownBy(() -> context.bindVariables(ConcatenationConnectorRequest.class))
                .isInstanceOf(ConnectorInputException.class)
                .hasMessageContaining("input1");
    }

    @Test
    void shouldFailWhenValidate_NoInput2() {
        var input = new ConcatenationConnectorRequest("input1", input2);

        var context = OutboundConnectorContextBuilder
                .create()
                .variables(input).build();

        assertThatThrownBy(() -> context.bindVariables(ConcatenationConnectorRequest.class))
                .isInstanceOf(ConnectorInputException.class)
                .hasMessageContaining("input2");
    }
}