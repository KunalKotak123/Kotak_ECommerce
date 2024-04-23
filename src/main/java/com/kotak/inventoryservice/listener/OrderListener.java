package com.kotak.inventoryservice.listener;

import com.kotak.inventoryservice.Dao.Order;
import com.kotak.inventoryservice.Dao.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class OrderListener {
    @KafkaListener(id = "orders", topics = "orders")
    public void onEvent(Order data) {
        System.out.printf("Order Received - {}%n");
    }
}
