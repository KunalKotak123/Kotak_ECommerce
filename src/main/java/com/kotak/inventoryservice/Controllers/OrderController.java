package com.kotak.inventoryservice.Controllers;

import com.kotak.inventoryservice.Dao.Order;
import com.kotak.inventoryservice.Dao.Product;
import com.kotak.inventoryservice.Services.OrderService;
import com.kotak.inventoryservice.Services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
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
        //this.redisService = redisService;
        this.template = template;

    }

    @GetMapping
    public List<Order> getAll() {
        return service.getAll();
    }

    @PutMapping("/add")
    public void createOrder(@RequestBody Order order) {
      //  redisService.decreaseOrderQuantity(p1.getProductId(), p1.getQuantity());
        service.add(order);

        // push it in kafka queue
        template.send("orders", order.getId(), order );
    }

    @PostMapping("/cancel")
    public void cancelOrder(@RequestBody Order order) {
      // cancel the order and send message to inventory service to bump up quantity.
        // Step 1: increase quantity in redis
        // Step 2: increase quantity in ddb
        // Step 3: update order status in orders table
    }

}

