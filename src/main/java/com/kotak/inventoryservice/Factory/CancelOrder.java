package com.kotak.inventoryservice.Factory;

import com.kotak.inventoryservice.Dao.Order;
import com.kotak.inventoryservice.Services.ProductService;

public class CancelOrder implements ProcessOrder {
    private final ProductService productService;
    public CancelOrder(ProductService productService)
    {
        this.productService = productService;
    }
    @Override
    public void processOrder(Order order) {
        productService.cancelOrder(order);
    }
}
