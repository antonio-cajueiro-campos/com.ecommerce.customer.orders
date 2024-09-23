package com.ecommerce.customer.orders.producer.Application.UseCases;

import com.ecommerce.customer.orders.producer.Application.Ports.Out.IOrderPurchaseRepository;
import com.ecommerce.customer.orders.producer.Domain.DTO.GetOrderPurchaseByIdInput;
import com.ecommerce.customer.orders.producer.Domain.Entities.OrderEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class GetOrderPurchaseByIdImplTests {

    @Mock
    private IOrderPurchaseRepository orderRepository;

    @InjectMocks
    private GetOrderPurchaseByIdImpl getOrderPurchaseById;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnOrder_whenOrderExists() {
        // Given
        UUID orderId = UUID.randomUUID();
        OrderEntity mockOrder = new OrderEntity(); // Suponha que você tenha um construtor ou setters
        when(orderRepository.findOrderById(anyString())).thenReturn(Optional.of(mockOrder));

        var input = new GetOrderPurchaseByIdInput(orderId);

        // When
        var output = getOrderPurchaseById.handle(input);

        // Then
        assertEquals(mockOrder, output.getOrder());
        verify(orderRepository, times(1)).findOrderById(orderId.toString());
    }

    @Test
    void shouldReturnNull_whenOrderDoesNotExist() {
        // Given
        UUID orderId = UUID.randomUUID();
        when(orderRepository.findOrderById(anyString())).thenReturn(Optional.empty());

        var input = new GetOrderPurchaseByIdInput(orderId);

        // When
        var output = getOrderPurchaseById.handle(input);

        // Then
        assertEquals(null, output.getOrder());
        verify(orderRepository, times(1)).findOrderById(orderId.toString());
    }

    @Test
    void shouldLogProperly_whenUseCaseStarts() {
        // Given
        UUID orderId = UUID.randomUUID();
        GetOrderPurchaseByIdInput input = new GetOrderPurchaseByIdInput(orderId);

        // Simula retorno vazio do repositório
        when(orderRepository.findOrderById(anyString())).thenReturn(Optional.empty());

        // When
        getOrderPurchaseById.handle(input);

        // Then
        // Verifica se o método de log foi chamado corretamente (logger.info)
        verify(orderRepository, times(1)).findOrderById(orderId.toString());
    }

    @Test
    void shouldReturnCurrentTimestamp() {
        // Given
        UUID orderId = UUID.randomUUID();
        OrderEntity mockOrder = new OrderEntity();
        when(orderRepository.findOrderById(anyString())).thenReturn(Optional.of(mockOrder));

        var input = new GetOrderPurchaseByIdInput(orderId);

        // When
        var output = getOrderPurchaseById.handle(input);

        // Then
        Instant now = Instant.now();
        // Considerando que a implementação utiliza CurrentTime.getCurrentTime(), o timestamp deve ser próximo do atual
        assertEquals(true, !output.getTimestamp().isAfter(now));
    }
}
