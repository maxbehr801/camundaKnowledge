package info.maxbehr.bpm.connector;

public record WatchServiceInboundConnectorProperties(
        String eventToMonitor,
        String directory,
        String pollingInterval
) {}
