package com.kotak.inventoryservice.listener;

import com.kotak.inventoryservice.Dao.Order;
import com.kotak.inventoryservice.Factory.OrderProcessingFactory;
import com.kotak.inventoryservice.Services.OrderService;
import com.kotak.inventoryservice.Services.ProductService;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class OrderListener {

    private ProductService productService;

    public OrderListener(ProductService orderService) {
        this.productService = orderService;
    }

    @KafkaListener(id = "orders", topics = "orders")
    public void onEvent(Order order) {
        var op = OrderProcessingFactory.getOrderProcessor(order.getStatus(),productService);
        op.processOrder(order);

    }

}
