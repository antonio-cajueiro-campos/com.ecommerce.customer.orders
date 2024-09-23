package com.ecommerce.customer.orders.producer.Adapters.Outbound.Kafka.Message;

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
