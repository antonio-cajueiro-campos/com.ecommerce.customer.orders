package com.ecommerce.customer.orders.producer.Application.Ports.Out;
public interface IMetricsService {
    void addGeneralError();
    void addBusinessError();
    void addSentEvents();
}
