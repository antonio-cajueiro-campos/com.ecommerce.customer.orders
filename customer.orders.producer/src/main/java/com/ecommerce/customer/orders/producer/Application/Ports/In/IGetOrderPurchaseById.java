package com.ecommerce.customer.orders.producer.Application.Ports.In;

import com.ecommerce.customer.orders.producer.Domain.DTO.GetOrderPurchaseByIdInput;
import com.ecommerce.customer.orders.producer.Domain.DTO.GetOrderPurchaseByIdOutput;

public interface IGetOrderPurchaseById {
    GetOrderPurchaseByIdOutput handle(GetOrderPurchaseByIdInput input);
}
