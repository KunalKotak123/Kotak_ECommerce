package com.kotak.inventoryservice.Controllers;

import com.kotak.inventoryservice.Dao.Product;
import com.kotak.inventoryservice.Response.ResponseHandler;
import com.kotak.inventoryservice.Services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<Product> getAll() {return productService.getAll();}

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
        productService.createProduct(product);
        return ResponseHandler.sendResponse("Successfully created a product", HttpStatus.OK);
    }

    @PutMapping
    public void updateProduct(@RequestBody Product p1) {
        productService.update(p1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable String id){
        productService.deleteProduct(id);
        return ResponseHandler.sendResponse("Successfully deleted a product", HttpStatus.OK);
    }

}
