package com.kotak.inventoryservice.Controllers;

import com.kotak.inventoryservice.Dao.Product;
import com.kotak.inventoryservice.Services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/countries")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping
    public List<Product> getAll() {
        return service.getAll();
    }

    @PutMapping("/add")
    public void add(@RequestBody Product p1) {
         service.add(p1);
    }
}
