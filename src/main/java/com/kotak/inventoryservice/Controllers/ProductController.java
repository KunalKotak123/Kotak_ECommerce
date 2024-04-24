package com.kotak.inventoryservice.Controllers;

import com.kotak.inventoryservice.Dao.Product;
import com.kotak.inventoryservice.Dao.ProductList;
import com.kotak.inventoryservice.Response.ResponseHandler;
import com.kotak.inventoryservice.Services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@EnableKafka
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService, KafkaTemplate<String, Product> template) {
        this.productService = productService;
        this.template = template;
    }

    @GetMapping
    public ProductList getAll() {return productService.getAll();}

    private KafkaTemplate<String, Product> template;
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable String id) {
        Product p1 = productService.getProductById(id);
        if(p1 != null){
            return ResponseHandler.sendResponse("Successfully fetched product details", HttpStatus.OK, p1);
        }
        else {
            return ResponseHandler.sendResponse("Failed to fetch product details", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody Product product){
        Product newProduct = productService.createProduct(product);

        var data = new HashMap<>();
        var productDetails = new HashMap<>();
        productDetails.put("id", newProduct.getId());
        productDetails.put("name", newProduct.getName());
        data.put("product_details", productDetails);

        return ResponseHandler.sendResponse("Successfully created a product", HttpStatus.OK, data);
    }

    @PutMapping
    public void updateProduct(@RequestBody Product p1) {
        productService.update(p1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable String id){
        productService.deleteProduct(id);
        return ResponseHandler.sendResponse("Successfully removed the product", HttpStatus.OK);
    }

}
