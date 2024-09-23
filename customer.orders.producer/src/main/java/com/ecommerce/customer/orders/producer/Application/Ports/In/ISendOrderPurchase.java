package com.ecommerce.customer.orders.producer.Application.Ports.In;

import com.ecommerce.customer.orders.producer.Domain.DTO.SendOrderPurchaseInput;
import com.ecommerce.customer.orders.producer.Domain.DTO.SendOrderPurchaseOutput;

public interface ISendOrderPurchase {
    SendOrderPurchaseOutput handle(SendOrderPurchaseInput input);
}
