package com.ecommerce.customer.orders.producer.Adapters.Outbounds;

import com.ecommerce.customer.orders.producer.Adapters.Outbound.Kafka.Message.OrderMessage;
import com.ecommerce.customer.orders.producer.Adapters.Outbound.Kafka.Producers.KafkaOrderPurchaseProducer;
import com.ecommerce.customer.orders.producer.Application.Ports.Out.IMetricsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class KafkaOrderPurchaseProducerTests {

    private KafkaTemplate<String, Object> kafkaTemplate;
    private IMetricsService metricsService;
    private KafkaOrderPurchaseProducer producer;

    @BeforeEach
    void setUp() {
        kafkaTemplate = Mockito.mock(KafkaTemplate.class);
        metricsService = Mockito.mock(IMetricsService.class);
        producer = new KafkaOrderPurchaseProducer(kafkaTemplate, metricsService);
    }

    @Test
    void shouldProduceSingleMessage() {
        // Given
        OrderMessage orderMessage = new OrderMessage();
        ArrayList<OrderMessage> messages = new ArrayList<>(List.of(orderMessage));
        String topic = "test-topic";

        // When
        producer.produce(messages, topic);

        // Then
        ArgumentCaptor<OrderMessage> messageCaptor = ArgumentCaptor.forClass(OrderMessage.class);
        verify(kafkaTemplate, times(1)).send(eq(topic), messageCaptor.capture());
        assertEquals(orderMessage, messageCaptor.getValue());
    }

    @Test
    void shouldProduceMultipleMessages() {
        // Given
        OrderMessage orderMessage1 = new OrderMessage();
        OrderMessage orderMessage2 = new OrderMessage();
        ArrayList<OrderMessage> messages = new ArrayList<>(List.of(orderMessage1, orderMessage2));
        String topic = "test-topic";

        // When
        producer.produce(messages, topic);

        // Then
        ArgumentCaptor<OrderMessage> messageCaptor = ArgumentCaptor.forClass(OrderMessage.class);
        verify(kafkaTemplate, times(2)).send(eq(topic), messageCaptor.capture());

        List<OrderMessage> capturedMessages = messageCaptor.getAllValues();
        assertEquals(2, capturedMessages.size());
        assertEquals(orderMessage1, capturedMessages.get(0));
        assertEquals(orderMessage2, capturedMessages.get(1));
    }

    @Test
    void shouldHandleEmptyMessagesList() {
        // Given
        ArrayList<OrderMessage> emptyMessages = new ArrayList<>();
        String topic = "test-topic";

        // When
        producer.produce(emptyMessages, topic);

        // Then
        verify(kafkaTemplate, never()).send(anyString(), any());
    }

    @Test
    void shouldLogErrorWhenSendFails() {
        // Given
        OrderMessage orderMessage = new OrderMessage();
        ArrayList<OrderMessage> messages = new ArrayList<>(List.of(orderMessage));
        String topic = "test-topic";

        // Simulate exception during send
        doThrow(new RuntimeException("Kafka send failed")).when(kafkaTemplate).send(eq(topic), any());

        // When
        producer.produce(messages, topic);

        // Then
        verify(kafkaTemplate, times(1)).send(eq(topic), any(OrderMessage.class));
    }
}