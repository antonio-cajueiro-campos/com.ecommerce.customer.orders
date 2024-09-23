package com.ecommerce.customer.orders.consumer.Domain.Constants;

public class EventTopics {
    public static final String MAIN_TOPIC = "purchase-orders";
    public static final String RETRY_TOPIC = "purchase-orders.retry";
    public static final String DLQ_TOPIC = "purchase-orders.dlq";
}
