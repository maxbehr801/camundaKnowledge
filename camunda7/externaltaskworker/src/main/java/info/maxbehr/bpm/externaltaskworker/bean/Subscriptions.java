package info.maxbehr.bpm.externaltaskworker.bean;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.spring.SpringTopicSubscription;
import org.camunda.bpm.client.spring.event.SubscriptionInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/handler")
@Slf4j
public class Subscriptions implements ApplicationListener<SubscriptionInitializedEvent> {

    private SpringTopicSubscription externalworkBean;

    @GetMapping("taskWorkerBean/status")
    public boolean taskWorkerBeanSubscriptionOpen() {
        return externalworkBean.isOpen();
    }

    @GetMapping("taskWorkerBean/open")
    public HttpStatus openExternalWorkHandlerBeanSubscription() {
        externalworkBean.open();
        return HttpStatus.OK;
    }

    @Override
    public void onApplicationEvent(SubscriptionInitializedEvent event) {
        SpringTopicSubscription springTopicSubscription = event.getSource();
        String topicName = springTopicSubscription.getTopicName();
        switch (topicName) {
            case "externalworkbean":
                externalworkBean = springTopicSubscription;
                break;
        }
        log.info("Subscription with topic name '{}' initialized", topicName);
    }
}
