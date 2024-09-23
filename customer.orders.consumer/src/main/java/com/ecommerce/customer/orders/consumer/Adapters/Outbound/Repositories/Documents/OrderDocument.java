package com.ecommerce.customer.orders.consumer.Adapters.Outbound.Repositories.Documents;

import com.ecommerce.customer.orders.consumer.Domain.Enums.OrderStatus;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Document(collection = "orders")
public class OrderDocument {
    @MongoId(targetType = FieldType.STRING)
    private String orderId;
    private String customerId;
    private BigDecimal amount;
    private OrderStatus status;
    private LocalDateTime date;
    private Instant timestamp;
    private Date createdAt;
}
