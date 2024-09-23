package com.ecommerce.customer.orders.consumer.Adapters.Beans;

import com.mongodb.client.model.IndexOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.bson.Document;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class MongoDBConfig {

    private final MongoTemplate mongoTemplate;

    @Bean
    public Void createTTLIndex() {
        var database = mongoTemplate.getDb();

        database.getCollection("orders").createIndex(
                new Document("createdAt", 1),
                new IndexOptions().expireAfter(30L, TimeUnit.DAYS)
        );
        return null;
    }
}
