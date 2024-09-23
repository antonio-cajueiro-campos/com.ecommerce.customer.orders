package com.ecommerce.customer.orders.producer.Adapters.Inbound.Web;

import com.ecommerce.customer.orders.producer.Adapters.Inbound.Web.Transports.SendOrderPurchaseTransport;
import com.ecommerce.customer.orders.producer.Domain.DTO.GetOrderPurchaseByIdInput;
import com.ecommerce.customer.orders.producer.Domain.DTO.SendOrderPurchaseInput;
import com.ecommerce.customer.orders.producer.Application.Ports.In.IGetOrderPurchaseById;
import com.ecommerce.customer.orders.producer.Application.Ports.In.ISendOrderPurchase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final ISendOrderPurchase sendOrderPurchase;

    private final IGetOrderPurchaseById getOrderPurchaseById;

    @PostMapping
    public ResponseEntity<?> sendOrderPurchaseEvent(@RequestBody SendOrderPurchaseTransport request) {
        var input = new SendOrderPurchaseInput(request.getOrders());

        var output = sendOrderPurchase.handle(input);

        return ResponseEntity.ok(output);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderPurchaseById(@PathVariable("id") UUID id) {
        var input = new GetOrderPurchaseByIdInput(id);

        var output = getOrderPurchaseById.handle(input);

        return ResponseEntity.ok(output);
    }
}
