package com.ecommerce.customer.orders.consumer.Adapters.Inbound.Kafka.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderMessage {
    private OrderData data;
    private Instant timestamp;
}
