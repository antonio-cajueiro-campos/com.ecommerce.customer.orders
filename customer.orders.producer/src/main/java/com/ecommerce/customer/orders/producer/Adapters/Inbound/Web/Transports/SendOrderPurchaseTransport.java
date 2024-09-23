package com.ecommerce.customer.orders.producer.Adapters.Inbound.Web.Transports;

import com.ecommerce.customer.orders.producer.Adapters.Outbound.Kafka.Message.OrderData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendOrderPurchaseTransport {
    private ArrayList<OrderData> orders;
}
