package com.ecommerce.customer.orders.consumer.Domain.Entities;

import com.ecommerce.customer.orders.consumer.Domain.Enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    private String orderId;
    private String customerId;
    private BigDecimal amount;
    private OrderStatus status;
    private LocalDateTime date;
    private Instant timestamp;
    private Date createdAt;

    public boolean HasInvalidAmount() {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }
}
