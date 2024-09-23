package com.ecommerce.customer.orders.producer.Adapters.Outbound.Kafka.Producers;

import com.ecommerce.customer.orders.producer.Adapters.Outbound.Kafka.Commons.ProducerBase;
import com.ecommerce.customer.orders.producer.Adapters.Outbound.Kafka.Message.OrderMessage;
import com.ecommerce.customer.orders.producer.Application.Ports.Out.IMetricsService;
import com.ecommerce.customer.orders.producer.Application.Ports.Out.IOrderPurchaseProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class KafkaOrderPurchaseProducer extends ProducerBase<OrderMessage> implements IOrderPurchaseProducer {

    public KafkaOrderPurchaseProducer(KafkaTemplate<String, Object> kafkaTemplate, IMetricsService metricsService) {
        super(kafkaTemplate, metricsService);
    }

    public void produce(ArrayList<OrderMessage> messages, String topic) {
        messages.forEach(message -> send(message, topic));
    }
}
