package com.ecommerce.customer.orders.producer.Application.Utils;

import java.time.Instant;
import java.time.OffsetDateTime;

public class CurrentTime {
    public static Instant getCurrentTime() {
        return OffsetDateTime.now().minusHours(3).toInstant();
    }
}
