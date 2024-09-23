package com.ecommerce.customer.orders.producer.Domain.Constants;

import lombok.Getter;

public class EventTopics {
    public static final String MAIN_TOPIC = "purchase-orders";
    public static final String RETRY_TOPIC = "purchase-orders.retry";
    public static final String DLQ_TOPIC = "purchase-orders.dlq";
}
