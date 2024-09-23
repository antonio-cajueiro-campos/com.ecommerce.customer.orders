package com.ecommerce.customer.orders.producer.Adapters.Outbound.Kafka.Commons;

import com.ecommerce.customer.orders.producer.Application.Ports.Out.IMetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.function.BiConsumer;

public abstract class ProducerBase<T> {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final IMetricsService metricsService;
    private static final Logger logger = LoggerFactory.getLogger(ProducerBase.class);
    public ProducerBase(KafkaTemplate<String, Object> kafkaTemplate, IMetricsService metricsService) {
        this.kafkaTemplate = kafkaTemplate;
        this.metricsService = metricsService;
    }

    protected void send(T data, String topic) {
        try {
            metricsService.addSentEvents();
            kafkaTemplate.send(topic, data).whenComplete(sentMessage(topic));
        } catch (Exception ex) {
            logger.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        }
    }

    private static BiConsumer<SendResult<String, Object>, Throwable> sentMessage(String topic) {
        return (success, ex) -> {
            if (ex != null)
                logger.error("Error producing event on {} topic", topic);
            else
                logger.info("Event produced in the {} topic successfully", topic);
        };
    }
}
