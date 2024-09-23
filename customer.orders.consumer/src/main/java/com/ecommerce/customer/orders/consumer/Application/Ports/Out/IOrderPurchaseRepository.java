package com.ecommerce.customer.orders.consumer.Application.Ports.Out;

import com.ecommerce.customer.orders.consumer.Domain.Entities.OrderEntity;

import java.util.Optional;

public interface IOrderPurchaseRepository {
    Optional<OrderEntity> findOrderById(String id);
    OrderEntity saveOrder(OrderEntity entity);
}
