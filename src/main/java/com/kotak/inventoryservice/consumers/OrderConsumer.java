package com.kotak.inventoryservice.consumers;

import com.kotak.inventoryservice.Dao.Order;
import com.kotak.inventoryservice.Enums.OrderStatus;
import com.kotak.inventoryservice.Factory.OrderProcessingFactory;
import com.kotak.inventoryservice.Services.ProductService;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class OrderConsumer {

    private final ProductService productService;

    public OrderConsumer(ProductService orderService) {
        this.productService = orderService;
    }

    @KafkaListener(id = "orders", topics = "orders")
    public void onEvent(Order order) {
        var op = OrderProcessingFactory.getOrderProcessor(OrderStatus.valueOf(order.getStatus()),productService);
        op.processOrder(order);
    }

}
