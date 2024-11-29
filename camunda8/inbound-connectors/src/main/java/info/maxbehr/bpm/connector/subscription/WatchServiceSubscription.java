package info.maxbehr.bpm.connector.subscription;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class WatchServiceSubscription {

    private static final Logger log = LoggerFactory.getLogger(WatchServiceSubscription.class);

    public WatchServiceSubscription(String eventToMonitor, String directory, String pollingInterval, Consumer<WatchServiceSubscriptionEvent> callback) {
        log.info("Activating WatcherService subscription");

        // listen to directory
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(directory);

            WatchKey watchKey = path.register(watchService, new WatchEvent.Kind[] {StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY});

            boolean isWatchKeyValid = true;

            while (isWatchKeyValid) {
                watchKey = watchService.poll(Long.parseLong(pollingInterval), TimeUnit.SECONDS);
                if (watchKey != null) {
                    for (WatchEvent<?> event : watchKey.pollEvents()) {
                        log.info("Event kind : {} - File : {} - event to monitor {}", event.kind(), event.context(), eventToMonitor);
                        if (eventToMonitor.equals(event.kind().toString())) {
                            WatchServiceSubscriptionEvent wsse = new WatchServiceSubscriptionEvent(eventToMonitor, directory, event.context().toString());
                            callback.accept(wsse);
                        }
                    }
                    watchKey.reset();
                } else {
                    log.info("No files during interval");
                }
            }
        } catch (Exception e) {
            log.error("Problem with connector: {}", e.getMessage());
        }
    }

    public void stop() {
        log.info("Deactivating file watcher service");
    }
}
