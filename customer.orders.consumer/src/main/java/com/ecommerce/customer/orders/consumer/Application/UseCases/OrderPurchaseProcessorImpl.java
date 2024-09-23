package com.ecommerce.customer.orders.consumer.Application.UseCases;

import com.ecommerce.customer.orders.consumer.Adapters.Inbound.Kafka.Consumers.KafkaOrderPurchaseConsumer;
import com.ecommerce.customer.orders.consumer.Application.Ports.Out.IOrderPurchaseRepository;
import com.ecommerce.customer.orders.consumer.Application.Ports.In.IOrderPurchaseProcessor;
import com.ecommerce.customer.orders.consumer.Domain.DTO.OrderPurchaseProcessorDTO;
import com.ecommerce.customer.orders.consumer.Domain.Entities.OrderEntity;
import com.ecommerce.customer.orders.consumer.Domain.Exceptions.BusinessException;
import com.ecommerce.customer.orders.consumer.Domain.Extensions.DomainExtensions;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
@ExtensionMethod(DomainExtensions.class)
public class OrderPurchaseProcessorImpl implements IOrderPurchaseProcessor {

    private static final Logger logger = LoggerFactory.getLogger(OrderPurchaseProcessorImpl.class);

    private final ModelMapper modelMapper;

    private final IOrderPurchaseRepository orderRepository;

    @Override
    @Timed(value = "order_process_time", percentiles = { 0.5, 0.99 }, description = "Time spent processing order")
    public void execute(OrderPurchaseProcessorDTO input) {
        logger.info("Starting UseCase {} with input {}", "OrderPurchaseProcessor", input);

        var order = createOrder(input);

        idempotencyValidation(order);

        parametersValidation(order);

        persistOrder(order, input.getTimestamp());
    }

    private OrderEntity createOrder(OrderPurchaseProcessorDTO input) {
        return modelMapper.map(input.getData(), OrderEntity.class);
    }

    @Timed(value = "idempotency_time", percentiles = { 0.5, 0.99 }, description = "Time spent validation idempotency")
    private void idempotencyValidation(OrderEntity order) {
        var orderDB = orderRepository.findOrderById(order.getOrderId());
        if (orderDB.isPresent()) throw new BusinessException("OrderId already exists");
    }

    private void parametersValidation(OrderEntity order) {
        if (order.HasInvalidAmount()) throw new BusinessException("Amount must be a positive number or zero");
    }

    @Timed(value = "persistence_time", percentiles = { 0.5, 0.99 }, description = "Time spent persisting data to the database")
    private void persistOrder(OrderEntity orderEntity, Instant timestamp) {
        orderEntity.setCreatedAt(new Date(timestamp.toEpochMilli()));
        orderEntity.setTimestamp(timestamp);
        orderRepository.saveOrder(orderEntity);
    }
}
