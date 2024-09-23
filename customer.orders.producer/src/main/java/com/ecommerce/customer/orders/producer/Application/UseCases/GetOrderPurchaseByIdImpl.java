package com.ecommerce.customer.orders.producer.Application.UseCases;

import com.ecommerce.customer.orders.producer.Application.Ports.In.IGetOrderPurchaseById;
import com.ecommerce.customer.orders.producer.Application.Ports.Out.IOrderPurchaseRepository;
import com.ecommerce.customer.orders.producer.Application.Utils.CurrentTime;
import com.ecommerce.customer.orders.producer.Domain.DTO.GetOrderPurchaseByIdInput;
import com.ecommerce.customer.orders.producer.Domain.DTO.GetOrderPurchaseByIdOutput;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetOrderPurchaseByIdImpl implements IGetOrderPurchaseById {

    private final IOrderPurchaseRepository orderRepository;
    private static final Logger logger = LoggerFactory.getLogger(GetOrderPurchaseByIdImpl.class);

    @Override
    @Timed(value = "get_order_time", percentiles = { 0.5, 0.99 }, description = "Time spent getting order")
    public GetOrderPurchaseByIdOutput handle(GetOrderPurchaseByIdInput input) {
        logger.info("Starting UseCase {} with input {}", "GetOrderPurchaseById", input);

        var order = orderRepository.findOrderById(input.getId().toString());

        return order.map(orderEntity ->
                new GetOrderPurchaseByIdOutput(CurrentTime.getCurrentTime(), orderEntity)).orElseGet(() ->
                new GetOrderPurchaseByIdOutput(CurrentTime.getCurrentTime(), null)
        );
    }
}
