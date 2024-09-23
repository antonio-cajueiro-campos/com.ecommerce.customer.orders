package com.ecommerce.customer.orders.producer.Application.UseCases;

import com.ecommerce.customer.orders.producer.Application.Ports.Out.IOrderPurchaseProducer;
import com.ecommerce.customer.orders.producer.Adapters.Outbound.Kafka.Message.OrderMessage;
import com.ecommerce.customer.orders.producer.Application.Ports.In.ISendOrderPurchase;
import com.ecommerce.customer.orders.producer.Application.Ports.Out.IOrderPurchaseRepository;
import com.ecommerce.customer.orders.producer.Application.Utils.CurrentTime;
import com.ecommerce.customer.orders.producer.Domain.Constants.EventTopics;
import com.ecommerce.customer.orders.producer.Domain.DTO.SendOrderPurchaseInput;
import com.ecommerce.customer.orders.producer.Domain.DTO.SendOrderPurchaseOutput;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class SendOrderPurchaseImpl implements ISendOrderPurchase {

    private final IOrderPurchaseRepository orderRepository;

    private final IOrderPurchaseProducer orderPurchaseProducer;
    private static final Logger logger = LoggerFactory.getLogger(SendOrderPurchaseImpl.class);

    @Override
    @Timed(value = "send_order_time", percentiles = { 0.5, 0.99 }, description = "Time spent send order")
    public SendOrderPurchaseOutput handle(SendOrderPurchaseInput input) {
        logger.info("Starting UseCase {} with input {}", "SendOrderPurchase", input);

        var orders = createOrderList(input);

        orderPurchaseProducer.produce(orders, EventTopics.MAIN_TOPIC);

        return new SendOrderPurchaseOutput(CurrentTime.getCurrentTime());
    }

    @Timed(value = "create_event_time", percentiles = { 0.5, 0.99 }, description = "Time spent creating order event")
    private ArrayList<OrderMessage> createOrderList(SendOrderPurchaseInput input) {
        var orders = new ArrayList<OrderMessage>();

        input.getOrders().forEach(order -> {
            var orderId = order.getOrderId();
            var exists = existsOrderValidation(orderId.toString());
            if (!exists)
                orders.add(new OrderMessage(order, CurrentTime.getCurrentTime()));
            else
                logger.warn("purchase order with id {} already exists", orderId);
        });

        return orders;
    }

    @Timed(value = "exists_validation_time", percentiles = { 0.5, 0.99 }, description = "Time spent validation if order already exists")
    private boolean existsOrderValidation(String orderId) {
        var order = orderRepository.findOrderById(orderId);
        return order.isPresent();
    }
}
