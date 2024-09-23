package com.ecommerce.customer.orders.producer.Application.UseCases;
import com.ecommerce.customer.orders.producer.Adapters.Outbound.Kafka.Message.OrderData;
import com.ecommerce.customer.orders.producer.Application.Ports.Out.IOrderPurchaseProducer;
import com.ecommerce.customer.orders.producer.Adapters.Outbound.Kafka.Message.OrderMessage;
import com.ecommerce.customer.orders.producer.Domain.DTO.SendOrderPurchaseInput;
import com.ecommerce.customer.orders.producer.Application.Ports.Out.IOrderPurchaseRepository;
import com.ecommerce.customer.orders.producer.Domain.Constants.EventTopics;
import com.ecommerce.customer.orders.producer.Domain.Entities.OrderEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SendOrderPurchaseImplTests {

    @Mock
    private IOrderPurchaseRepository orderRepository;

    @Mock
    private IOrderPurchaseProducer orderPurchaseProducer;

    @InjectMocks
    private SendOrderPurchaseImpl sendOrderPurchase;

    private SendOrderPurchaseInput validInput;

    @BeforeEach
    void setUp() {
        validInput = new SendOrderPurchaseInput();
        validInput.setOrders(createMockOrderEntities());
    }

    private List<OrderData> createMockOrderEntities() {
        List<OrderData> orders = new ArrayList<>();
        var order1 = new OrderData();
        order1.setOrderId(UUID.randomUUID());
        orders.add(order1);

        var order2 = new OrderData();
        order2.setOrderId(UUID.randomUUID());
        orders.add(order2);

        return orders;
    }

    @Test
    void shouldHandleSuccessfully_whenOrdersAreValid() {
        // Quando não existem ordens com os IDs fornecidos
        when(orderRepository.findOrderById(anyString())).thenReturn(Optional.empty());

        // Executa o método handle
        sendOrderPurchase.handle(validInput);

        // Verifica se a ordem foi processada corretamente
        verify(orderPurchaseProducer, times(1)).produce(argThat(list ->
                list != null && list.stream().allMatch(Objects::nonNull)
        ), eq(EventTopics.MAIN_TOPIC));
    }

    @Test
    void shouldSendEmptyList_whenOrderIdAlreadyExists() {
        // Simula uma order na base
        var uuid = UUID.randomUUID();
        var orderEntity = new OrderEntity();
        orderEntity.setOrderId(uuid.toString());
        when(orderRepository.findOrderById(uuid.toString())).thenReturn(Optional.of(orderEntity));

        // Criando request com order ja existente na base
        var orders = new ArrayList<OrderData>();
        var orderRequest = new OrderData();
        orderRequest.setOrderId(uuid);
        orders.add(orderRequest);
        var input = new SendOrderPurchaseInput(orders);

        // Executa o método handle
        sendOrderPurchase.handle(input);

        // Verifica se o produtor não foi chamado
        verify(orderPurchaseProducer, times(1)).produce(new ArrayList<>(), EventTopics.MAIN_TOPIC);
    }
}
