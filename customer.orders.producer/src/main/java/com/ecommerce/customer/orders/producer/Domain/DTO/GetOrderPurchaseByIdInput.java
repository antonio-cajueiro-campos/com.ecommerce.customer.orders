package com.ecommerce.customer.orders.producer.Domain.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderPurchaseByIdInput {
    @NotBlank
    private UUID id;
}
