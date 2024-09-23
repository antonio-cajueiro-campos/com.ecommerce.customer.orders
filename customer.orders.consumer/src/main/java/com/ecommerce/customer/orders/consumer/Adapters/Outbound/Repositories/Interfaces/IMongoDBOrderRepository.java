package com.ecommerce.customer.orders.consumer.Adapters.Outbound.Repositories.Interfaces;

import com.ecommerce.customer.orders.consumer.Adapters.Outbound.Repositories.Documents.OrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IMongoDBOrderRepository extends MongoRepository<OrderDocument, String> {
}
