package info.maxbehr.bpm.externaltaskworker.bean;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.spring.SpringTopicSubscription;
import org.camunda.bpm.client.spring.event.SubscriptionInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/handler")
@Slf4j
public class Subscriptions implements ApplicationListener<SubscriptionInitializedEvent> {

    private final SpringTopicSubscription externalWorkHandlerSubscription;
    private final SpringTopicSubscription taskWorkerSubscription;

    @GetMapping("taskWorker/status")
    public boolean taskWorkerSubscriptionOpen() {
        return taskWorkerSubscription.isOpen();
    }

    @GetMapping("taskWorker/open")
    public HttpStatus openExternalWorkHandlerSubscription() {
        taskWorkerSubscription.open();
        return HttpStatus.OK;
    }

    public Subscriptions(SpringTopicSubscription externalWorkHandlerSubscription, SpringTopicSubscription taskWorkerSubscription) {
        this.externalWorkHandlerSubscription = externalWorkHandlerSubscription;
        this.taskWorkerSubscription = taskWorkerSubscription;
    }

    @PostConstruct
    public void listSubscriptionBeans() {
        log.info("Subscription bean 'externalWorkHandlerSubscription' has topic name {}", externalWorkHandlerSubscription.getTopicName());
        log.info("Subscription bean 'taskWorkerSubscription' has topic name {}", taskWorkerSubscription.getTopicName());
    }

    @Override
    public void onApplicationEvent(SubscriptionInitializedEvent event) {
        SpringTopicSubscription springTopicSubscription = event.getSource();
        String topicName = springTopicSubscription.getTopicName();
        log.info("Subscription with topic name '{}' initialized", topicName);
    }
}
