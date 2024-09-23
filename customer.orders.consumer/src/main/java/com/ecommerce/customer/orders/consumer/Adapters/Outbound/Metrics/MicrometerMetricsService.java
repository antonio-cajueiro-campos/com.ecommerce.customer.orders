package com.ecommerce.customer.orders.consumer.Adapters.Outbound.Metrics;

import com.ecommerce.customer.orders.consumer.Application.Ports.Out.IMetricsService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MicrometerMetricsService implements IMetricsService {
    private final Counter generalErrorCounter;
    private final Counter businessErrorCounter;
    private final Counter receivedMainTopicEventsCounter;
    private final Counter receivedRetryTopicEventsCounter;
    private final Counter receivedDLQTopicEventsCounter;

    @Autowired
    public MicrometerMetricsService(MeterRegistry registry) {
        this.generalErrorCounter = Counter.builder("general_error_count")
                .description("General Errors Counter")
                .tag("Type", "Error")
                .register(registry);

        this.businessErrorCounter = Counter.builder("business_error_count")
                .description("Business Errors Counter")
                .tag("Type", "Error")
                .register(registry);

        this.receivedMainTopicEventsCounter = Counter.builder("received_main_events_count")
                .description("Received Main Events Counter")
                .tag("Type", "Info")
                .tag("Level", "Main")
                .register(registry);

        this.receivedRetryTopicEventsCounter = Counter.builder("received_retry_events_count")
                .description("Received Retry Events Counter")
                .tag("Type", "Info")
                .tag("Level", "Retry")
                .register(registry);

        this.receivedDLQTopicEventsCounter = Counter.builder("received_dlq_events_count")
                .description("Received DLQ Events Counter")
                .tag("Type", "Info")
                .tag("Level", "DLQ")
                .register(registry);
    }

    public void addGeneralError() {
        this.generalErrorCounter.increment();
    }

    public void addBusinessError() {
        this.businessErrorCounter.increment();
    }

    public void addReceivedMainTopicEvents() {
        this.receivedMainTopicEventsCounter.increment();
    }
    public void addReceivedRetryTopicEvents() {
        this.receivedRetryTopicEventsCounter.increment();
    }
    public void addReceivedDLQTopicEvents() {
        this.receivedDLQTopicEventsCounter.increment();
    }
}
