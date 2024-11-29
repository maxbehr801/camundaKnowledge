package info.maxbehr.bpm.connector;

import info.maxbehr.bpm.connector.subscription.WatchServiceSubscription;
import info.maxbehr.bpm.connector.subscription.WatchServiceSubscriptionEvent;
import io.camunda.connector.api.annotation.InboundConnector;
import io.camunda.connector.api.inbound.InboundConnectorContext;
import io.camunda.connector.api.inbound.InboundConnectorExecutable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@InboundConnector(
        name = "Watch Service Inbound Connector",
        type = "io.camunda:watchserviceinbound:1"
)
public class WatchServiceInboundConnectorExecutable implements InboundConnectorExecutable<InboundConnectorContext> {
  private WatchServiceSubscription subscription;
  private InboundConnectorContext context;
  private ExecutorService executorService;
  public CompletableFuture<?> future;

  @Override
  public void activate(InboundConnectorContext context) {
    WatchServiceInboundConnectorProperties props = context.bindProperties(WatchServiceInboundConnectorProperties.class);
    this.context = context;
    this.executorService = Executors.newSingleThreadExecutor();

    this.future =
            CompletableFuture.runAsync(
                    () -> new WatchServiceSubscription(
                            props.eventToMonitor(),
                            props.directory(),
                            props.pollingInterval(),
                            this::onEvent),
                    this.executorService);
  }

  private void onEvent(WatchServiceSubscriptionEvent rawEvent) {
    WatchServiceInboundConnectorEvent connectorEvent = new WatchServiceInboundConnectorEvent(rawEvent);
    context.correlateWithResult(connectorEvent);
  }

  @Override
  public void deactivate() {
    subscription.stop();
  }
}
