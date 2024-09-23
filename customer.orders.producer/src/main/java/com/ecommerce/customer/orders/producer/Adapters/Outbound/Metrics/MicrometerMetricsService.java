package com.ecommerce.customer.orders.producer.Adapters.Outbound.Metrics;

import com.ecommerce.customer.orders.producer.Application.Ports.Out.IMetricsService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MicrometerMetricsService implements IMetricsService {
    private final Counter generalErrorCounter;
    private final Counter businessErrorCounter;
    private final Counter sentEventsCounter;

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

        this.sentEventsCounter = Counter.builder("sent_events_count")
                .description("Sent Events Counter")
                .tag("Type", "Info")
                .register(registry);
    }

    public void addGeneralError() {
        this.generalErrorCounter.increment();
    }

    public void addBusinessError() {
        this.businessErrorCounter.increment();
    }

    public void addSentEvents() {
        this.sentEventsCounter.increment();
    }
}
