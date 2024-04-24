package com.kotak.inventoryservice.consumers;

import com.kotak.inventoryservice.Dao.Order;
import com.kotak.inventoryservice.Enums.OrderStatus;
import com.kotak.inventoryservice.Services.OrderService;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class ProductConsumer {

    private final OrderService orderService;

    public ProductConsumer(OrderService productService) {
        this.orderService = productService;
    }

    @KafkaListener(id = "stock-orders", topics = "stock-orders")
    public void onAcceptOrRejectEvent(Order order) {
        System.out.println(order.getStatus());
        if (order.getStatus().equals(OrderStatus.ACCEPTED.getValue())) {
            orderService.processOrder(order);
        }
        else if(order.getStatus().equals(OrderStatus.REJECTED.getValue())){
            orderService.rejectOrder(order);
        }
        else if(order.getStatus().equals(OrderStatus.CANCELED.getValue())){
            orderService.confirmCancellation(order);
        }
    }
}
