package com.kotak.inventoryservice.Controllers;

import com.kotak.inventoryservice.Dao.Order;
import com.kotak.inventoryservice.Services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@EnableKafka
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService service;
    private final KafkaTemplate<String, Order> template;

    public OrderController(OrderService service, KafkaTemplate<String, Order> template) {
        this.service = service;
        this.template = template;

    }

    @GetMapping
    public List<Order> getAll() {
        return service.getAll();
    }

    @PostMapping
    public void createOrder(@RequestBody Order order) {
        service.add(order);
        template.send("orders", order.getId(), order);
    }


    @PostMapping("/cancel/{id}")
    public void cancelOrder(@PathVariable String id) {
        service.cancelOrder(id);
    }

}

