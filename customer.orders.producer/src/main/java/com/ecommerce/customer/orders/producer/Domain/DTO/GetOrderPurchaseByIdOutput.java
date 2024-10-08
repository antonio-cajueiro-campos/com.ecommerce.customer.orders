package com.ecommerce.customer.orders.producer.Domain.DTO;

import com.ecommerce.customer.orders.producer.Domain.Entities.OrderEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderPurchaseByIdOutput {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Instant timestamp;
    private OrderEntity order;
}
