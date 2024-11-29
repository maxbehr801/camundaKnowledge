package info.maxbehr.bpm.connector.subscription;

public record WatchServiceSubscriptionEvent(
        String monitoredEvent,
        String directory,
        String fileName
) {}
