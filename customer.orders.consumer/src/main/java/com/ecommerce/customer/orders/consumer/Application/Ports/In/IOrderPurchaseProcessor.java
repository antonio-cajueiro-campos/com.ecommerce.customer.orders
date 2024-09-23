package com.ecommerce.customer.orders.consumer.Application.Ports.In;

import com.ecommerce.customer.orders.consumer.Domain.DTO.OrderPurchaseProcessorDTO;

public interface IOrderPurchaseProcessor {
    void execute(OrderPurchaseProcessorDTO input);
}
