package com.ecommerce.customer.orders.consumer.Adapters.Beans;

import com.mongodb.ReadConcern;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.IndexOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.bson.Document;
import org.springframework.data.mongodb.core.convert.MongoConverter;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class MongoDBConfig {

    private final MongoClient mongoClient;

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDbFactory, MongoConverter converter) {
        var mongoTemplate = new MongoTemplate(mongoDbFactory, converter);
        mongoTemplate.setWriteConcern(WriteConcern.MAJORITY);
        mongoTemplate.getDb().withReadConcern(ReadConcern.MAJORITY);
        return mongoTemplate;
    }

    @Bean
    public Void createTTLIndex() {
        var database = mongoClient.getDatabase("orders");

        database.getCollection("orders").createIndex(
                new Document("createdAt", 1),
                new IndexOptions().expireAfter(30L, TimeUnit.DAYS)
        );
        return null;
    }
}
