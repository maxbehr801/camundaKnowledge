package info.maxbehr.bpm.connector;

import io.camunda.connector.test.inbound.InboundConnectorContextBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FileWatchConnectorTest {

    private String eventToMonitor = "ENTRY_CREATE";
    private String directory = "C:\\Users\\Camunda\\Downloads";
    private String pollingInterval = "30";

    @Test
    void shouldFailWhenValidate_NoEventToMontior() {
        var input = new WatchServiceInboundConnectorProperties(eventToMonitor, directory, pollingInterval);
        var context = InboundConnectorContextBuilder.create().properties(input).build();

        var connectorInput = context.bindProperties(WatchServiceInboundConnectorProperties.class);

        assertThat(connectorInput)
                .isInstanceOf(WatchServiceInboundConnectorProperties.class)
                .extracting("eventToMonitor")
                .isEqualTo("ENTRY_CREATE");

    }

    @Test
    void shouldFailWhenValidate_NoDirectory() {
        var input = new WatchServiceInboundConnectorProperties(eventToMonitor, directory, pollingInterval);
        var context = InboundConnectorContextBuilder.create().properties(input).build();

        var connectorInput = context.bindProperties(WatchServiceInboundConnectorProperties.class);

        assertThat(connectorInput)
                .isInstanceOf(WatchServiceInboundConnectorProperties.class)
                .extracting("directory")
                .isEqualTo("C:\\Users\\Camunda\\Downloads");
    }

    @Test
    void shouldFailWhenValidate_NoPollingInterval() {
        var input = new WatchServiceInboundConnectorProperties(eventToMonitor, directory, pollingInterval);
        var context = InboundConnectorContextBuilder.create().properties(input).build();

        var connectorInput = context.bindProperties(WatchServiceInboundConnectorProperties.class);

        assertThat(connectorInput)
                .isInstanceOf(WatchServiceInboundConnectorProperties.class)
                .extracting("pollingInterval")
                .isEqualTo("30");
    }
}
