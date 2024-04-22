package com.kotak.inventoryservice.Controllers;

import com.kotak.inventoryservice.Dao.Product;
import com.kotak.inventoryservice.Response.ResponseHandler;
import com.kotak.inventoryservice.Services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping
    public List<Product> getAll() {return service.getAll();}

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable String id) {
        Product p1 = service.getProductById(id);
        if(p1 != null){
            return ResponseHandler.sendResponse("Successfully fetched product details", HttpStatus.OK, p1);
        }
        else {
            return ResponseHandler.sendResponse("Product details not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody Product product){
        service.createProduct(product);
        return ResponseHandler.sendResponse("Successfully created a product", HttpStatus.OK);
    }

    @PutMapping
    public void add(@RequestBody Product p1) {
         service.add(p1);
    }

}
