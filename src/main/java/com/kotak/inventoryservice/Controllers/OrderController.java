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
    private KafkaTemplate<String, Order> template;

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
    public void add(@RequestBody Order o) {
      //  redisService.decreaseOrderQuantity(p1.getProductId(), p1.getQuantity());
        service.add(o);
        //template.send("orders", p1.getProductId(), p1 );
    }
}
