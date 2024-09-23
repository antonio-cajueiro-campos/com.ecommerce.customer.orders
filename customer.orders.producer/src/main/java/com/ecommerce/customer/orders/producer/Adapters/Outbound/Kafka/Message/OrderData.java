package com.ecommerce.customer.orders.producer.Adapters.Outbound.Kafka.Message;

import com.ecommerce.customer.orders.producer.Domain.Enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderData {
    private UUID orderId;
    private UUID customerId;
    private BigDecimal amount;
    private OrderStatus status;
    private LocalDateTime date;
}
