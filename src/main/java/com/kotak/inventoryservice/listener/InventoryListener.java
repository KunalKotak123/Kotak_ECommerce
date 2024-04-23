package com.kotak.inventoryservice.listener;

import com.kotak.inventoryservice.Dao.Order;
import com.kotak.inventoryservice.Services.OrderService;
import com.kotak.inventoryservice.Services.ProductService;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class InventoryListener {

    private OrderService orderService;

    public InventoryListener(OrderService productService) {
        this.orderService = productService;
    }

    @KafkaListener(id = "stock-orders", topics = "stock-orders")
    public void onAcceptOrRejectEvent(Order order) {
    // ACCEPT _ REJECT
        // if accept then sid will update order in ddb saying confirmed
        // and push a message in oder queue saying order is confirmed
        // if reject he will update ddb with order_cancel status
    }
}
