package com.ecommerce.customer.orders.consumer.Domain.DTO;

import com.ecommerce.customer.orders.consumer.Adapters.Inbound.Kafka.Message.OrderData;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPurchaseProcessorDTO {
    @Valid
    private OrderData data;
    private Instant timestamp;
}
