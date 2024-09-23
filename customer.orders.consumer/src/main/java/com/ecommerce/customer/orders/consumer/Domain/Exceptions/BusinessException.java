package com.ecommerce.customer.orders.consumer.Domain.Exceptions;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
