package com.kotak.inventoryservice.Controllers;

import com.kotak.inventoryservice.Dao.Product;
import com.kotak.inventoryservice.Services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@EnableKafka
@RequestMapping("/api/v1/countries")
public class ProductController {
    private final ProductService service;
    private KafkaTemplate<Long, Product> template;

    public ProductController(ProductService service, KafkaTemplate<Long, Product> template) {
        this.service = service;
        this.template = template;
    }

    @GetMapping
    public List<Product> getAll() {
        return service.getAll();
    }

    @PutMapping("/add")
    public void add(@RequestBody Product p1) {
         service.add(p1);
         template.send("orders", p1.getProductId(), p1 );
    }

    @KafkaListener(id = "orders", topics = "orders")
    public void onEvent(Product data) {
        System.out.printf("Order Received - {}%n");
    }
}
