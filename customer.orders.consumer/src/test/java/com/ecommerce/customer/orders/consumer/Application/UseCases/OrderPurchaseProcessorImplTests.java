package com.ecommerce.customer.orders.consumer.Application.UseCases;

import com.ecommerce.customer.orders.consumer.Application.Ports.Out.IMetricsService;
import com.ecommerce.customer.orders.consumer.Application.Ports.Out.IOrderPurchaseRepository;
import com.ecommerce.customer.orders.consumer.Domain.DTO.OrderPurchaseProcessorDTO;
import com.ecommerce.customer.orders.consumer.Domain.Entities.OrderEntity;
import com.ecommerce.customer.orders.consumer.Domain.Exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderPurchaseProcessorImplTests {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private IMetricsService metricsService;

    @Mock
    private IOrderPurchaseRepository orderRepository;

    @InjectMocks
    private OrderPurchaseProcessorImpl orderPurchaseProcessor;

    private OrderPurchaseProcessorDTO validInput;
    private OrderEntity orderEntity;

    @BeforeEach
    void setUp() {
        // Inicializando objetos de teste
        validInput = new OrderPurchaseProcessorDTO();
        validInput.setTimestamp(Instant.now());
        // Preenche validInput com os dados que deseja testar

        orderEntity = new OrderEntity();
        orderEntity.setOrderId("valid-order-id");
        orderEntity.setAmount(BigDecimal.valueOf(100.00));

        when(modelMapper.map(any(), eq(OrderEntity.class))).thenReturn(orderEntity);
    }

    @Test
    void shouldExecuteSuccessfully_whenValidInput() {
        // Quando a ordem não existe no banco de dados
        when(orderRepository.findOrderById(anyString())).thenReturn(Optional.empty());

        // Executa o método que será testado
        assertDoesNotThrow(() -> orderPurchaseProcessor.execute(validInput));

        // Verifica se a ordem foi salva e as métricas foram registradas
        verify(orderRepository, times(1)).saveOrder(orderEntity);
    }

    @Test
    void shouldThrowException_whenOrderIdAlreadyExists() {
        // Quando a ordem já existe no banco de dados
        when(orderRepository.findOrderById(anyString())).thenReturn(Optional.of(orderEntity));

        // Verifica se uma exceção é lançada
        Exception exception = assertThrows(BusinessException.class, () -> {
            orderPurchaseProcessor.execute(validInput);
        });

        // Verifica o conteúdo da mensagem de erro
        assertEquals("OrderId already exists", exception.getMessage());

        // Verifica que a ordem não foi salva
        verify(orderRepository, never()).saveOrder(any());
    }

    @Test
    void shouldThrowException_whenAmountIsInvalid() {
        // Simula um pedido com valor inválido
        orderEntity.setAmount(BigDecimal.valueOf(-10.00));
        when(orderRepository.findOrderById(anyString())).thenReturn(Optional.empty());

        // Verifica se uma exceção é lançada
        Exception exception = assertThrows(BusinessException.class, () -> {
            orderPurchaseProcessor.execute(validInput);
        });

        // Verifica o conteúdo da mensagem de erro
        assertEquals("Amount must be a positive number or zero", exception.getMessage());

        // Verifica que a ordem não foi salva
        verify(orderRepository, never()).saveOrder(any());
    }
}