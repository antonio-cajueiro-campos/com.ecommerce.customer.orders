package com.ecommerce.customer.orders.consumer.Application.Ports.Out;
public interface IMetricsService {
    void addGeneralError();
    void addBusinessError();
    void addReceivedMainTopicEvents();
    void addReceivedRetryTopicEvents();
    void addReceivedDLQTopicEvents();
}
