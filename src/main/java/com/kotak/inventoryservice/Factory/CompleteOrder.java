package com.kotak.inventoryservice.Factory;


import com.kotak.inventoryservice.Dao.Order;
import com.kotak.inventoryservice.Services.ProductService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompleteOrder implements ProcessOrder {
    private final ProductService productService;
    public CompleteOrder(ProductService productService) {
        this.productService = productService;
    }
    @Override
    public void processOrder(Order order)
    {
        log.info("[Trace Order]  Complete Order " + order);
        productService.completeOrder(order);
    }
}
