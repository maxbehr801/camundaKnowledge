package info.maxbehr.bpm.connector;

import info.maxbehr.bpm.connector.subscription.WatchServiceSubscriptionEvent;

/**
 * Data model of an event produced by the inbound Connector
 *
 * @param event
 */
public record WatchServiceInboundConnectorEvent(WatchServiceSubscriptionEvent event) {}
