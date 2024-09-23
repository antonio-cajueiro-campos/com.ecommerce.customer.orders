package com.ecommerce.customer.orders.producer.Domain.DTO;

import com.ecommerce.customer.orders.producer.Adapters.Outbound.Kafka.Message.OrderData;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendOrderPurchaseInput {
    @NotEmpty(message = "Input orders list cannot be empty.")
    private List<OrderData> orders;
}
