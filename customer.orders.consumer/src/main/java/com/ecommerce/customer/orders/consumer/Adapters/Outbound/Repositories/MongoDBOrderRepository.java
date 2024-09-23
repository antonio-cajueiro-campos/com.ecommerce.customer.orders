package com.ecommerce.customer.orders.consumer.Adapters.Outbound.Repositories;

import com.ecommerce.customer.orders.consumer.Adapters.Inbound.Kafka.Message.OrderData;
import com.ecommerce.customer.orders.consumer.Adapters.Inbound.Kafka.Message.OrderMessage;
import com.ecommerce.customer.orders.consumer.Adapters.Outbound.Repositories.Documents.OrderDocument;
import com.ecommerce.customer.orders.consumer.Adapters.Outbound.Repositories.Interfaces.IMongoDBOrderRepository;
import com.ecommerce.customer.orders.consumer.Application.Ports.Out.IOrderPurchaseRepository;
import com.ecommerce.customer.orders.consumer.Application.UseCases.OrderPurchaseProcessorImpl;
import com.ecommerce.customer.orders.consumer.Domain.Constants.EventTopics;
import com.ecommerce.customer.orders.consumer.Domain.Entities.OrderEntity;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MongoDBOrderRepository implements IOrderPurchaseRepository {

    private final IMongoDBOrderRepository mongoDBOrderRepository;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(MongoDBOrderRepository.class);

    @CircuitBreaker(name = "mongoDBOrderRepository")
    public Optional<OrderEntity> findOrderById(String id) {
        var orderOptionalDocument = mongoDBOrderRepository.findById(id);
        return orderOptionalDocument.map(order -> modelMapper.map(order, OrderEntity.class));
    }

    @CircuitBreaker(name = "mongoDBOrderRepository", fallbackMethod = "fallbackSaveOrder")
    public OrderEntity saveOrder(OrderEntity entity) {
        var orderDocument = modelMapper.map(entity, OrderDocument.class);
        var orderResult = mongoDBOrderRepository.save(orderDocument);
        return modelMapper.map(orderResult, OrderEntity.class);
    }

    public void fallbackSaveOrder(OrderEntity entity, Throwable t) {
        var orderData = modelMapper.map(entity, OrderData.class);
        var orderMessage = new OrderMessage();
        orderMessage.setTimestamp(entity.getTimestamp());
        orderMessage.setData(orderData);
        kafkaTemplate.send(EventTopics.RETRY_TOPIC, orderMessage);
    }
}
