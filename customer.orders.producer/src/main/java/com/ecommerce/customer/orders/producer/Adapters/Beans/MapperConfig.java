package com.ecommerce.customer.orders.producer.Adapters.Beans;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        var toInstant = new Converter<String, Instant>() {
            public Instant convert(MappingContext<String, Instant> context) {
                return Instant.parse(context.getSource());
            }
        };
        modelMapper.addConverter(toInstant);
        return modelMapper;
    }

    @Bean
    public ObjectMapper objectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
        return objectMapper;
    }
}
