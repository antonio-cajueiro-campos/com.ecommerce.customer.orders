package com.ecommerce.customer.orders.producer.Application.Ports.Out;

import com.ecommerce.customer.orders.producer.Adapters.Outbound.Kafka.Message.OrderMessage;

import java.util.ArrayList;

public interface IOrderPurchaseProducer {
    void produce(ArrayList<OrderMessage> messages, String topic);
}
