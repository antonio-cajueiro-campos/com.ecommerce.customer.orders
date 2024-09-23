package com.ecommerce.customer.orders.producer.Application.Ports.Out;

import com.ecommerce.customer.orders.producer.Domain.Entities.OrderEntity;

import java.util.Optional;

public interface IOrderPurchaseRepository {
    Optional<OrderEntity> findOrderById(String id);
}
