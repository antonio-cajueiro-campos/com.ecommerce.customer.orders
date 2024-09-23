package com.ecommerce.customer.orders.producer.Adapters.Outbound.Repositories;

import com.ecommerce.customer.orders.producer.Adapters.Outbound.Repositories.Documents.OrderDocument;
import com.ecommerce.customer.orders.producer.Adapters.Outbound.Repositories.Interfaces.IMongoDBOrderRepository;
import com.ecommerce.customer.orders.producer.Application.Ports.Out.IOrderPurchaseRepository;
import com.ecommerce.customer.orders.producer.Domain.Entities.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MongoDBOrderRepository implements IOrderPurchaseRepository {

    private final IMongoDBOrderRepository mongoDBOrderRepository;

    private final ModelMapper modelMapper;

    public Optional<OrderEntity> findOrderById(String id) {
        var orderOptionalDocument = mongoDBOrderRepository.findById(id);
        return orderOptionalDocument.map(order -> modelMapper.map(order, OrderEntity.class));
    }
}
