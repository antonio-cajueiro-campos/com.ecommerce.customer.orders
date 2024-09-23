package com.ecommerce.customer.orders.consumer.Adapters.Inbound.Kafka.Consumers;

import com.ecommerce.customer.orders.consumer.Application.Ports.Out.IMetricsService;
import com.ecommerce.customer.orders.consumer.Domain.DTO.OrderPurchaseProcessorDTO;
import com.ecommerce.customer.orders.consumer.Application.Ports.In.IOrderPurchaseProcessor;
import com.ecommerce.customer.orders.consumer.Domain.Constants.EventTopics;
import com.ecommerce.customer.orders.consumer.Domain.Exceptions.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class KafkaOrderPurchaseConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaOrderPurchaseConsumer.class);

    private final ObjectMapper objectMapper;

    private final IOrderPurchaseProcessor orderPurchaseProcessor;

    private final IMetricsService metricsService;

    @Timed(
            value = "consumption_main_time",
            percentiles = { 0.5, 0.99 },
            description = "Time spent processing Kafka messages in main topic"
    )
    @KafkaListener(
            topics = EventTopics.MAIN_TOPIC,
            groupId = "ecommerce",
            containerFactory = "containerFactory"
    )
    private void consumer(ConsumerRecord<String, Object> record, Acknowledgment acknowledgment) throws Exception {
        metricsService.addReceivedMainTopicEvents();
        logger.info("Consume event: {}", record);
        executeUseCase(record, acknowledgment);
    }

    @Timed(
            value = "consumption_retry_time",
            percentiles = { 0.5, 0.99 },
            description = "Time spent processing Kafka messages in retry topic"
    )
    @KafkaListener(
            topics = EventTopics.RETRY_TOPIC,
            groupId = "ecommerce",
            containerFactory = "containerFactory"
    )
    private void consumeRetry(ConsumerRecord<String, Object> record, Acknowledgment acknowledgment) throws Exception {
        metricsService.addReceivedRetryTopicEvents();
        logger.info("Retry consume event: {}", record);
        executeUseCase(record, acknowledgment);
    }

    @KafkaListener(
            topics = EventTopics.DLQ_TOPIC,
            groupId = "ecommerce"
    )
    private void consumeDeadLetter(ConsumerRecord<String, Object> record) {
        metricsService.addReceivedDLQTopicEvents();
        logger.info("DLQ consume event: {}", record);
    }

    private void executeUseCase(ConsumerRecord<String, Object> record, Acknowledgment acknowledgment) throws Exception {
        try {
            var input = objectMapper.readValue(record.value().toString(), OrderPurchaseProcessorDTO.class);
            orderPurchaseProcessor.execute(input);
            acknowledgment.acknowledge();
        } catch (BusinessException e) {
            metricsService.addBusinessError();
            logger.warn("An business error occurred while consuming event: {} | {} ", e.getMessage(), record);
            acknowledgment.acknowledge();
        } catch (Exception e) {
            metricsService.addGeneralError();
            logger.error("An unexpected error occurred while consuming event: {} | {} ", e.getMessage(), record, e);
            throw e;
        }
    }
}
