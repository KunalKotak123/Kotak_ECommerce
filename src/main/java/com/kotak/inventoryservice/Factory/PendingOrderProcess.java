package com.kotak.inventoryservice.Factory;

import com.kotak.inventoryservice.Dao.Order;
import com.kotak.inventoryservice.Services.ProductService;

public class PendingOrderProcess implements ProcessOrder {
    private final ProductService productService;

    public PendingOrderProcess(ProductService productService) {
        this.productService = productService;
    }
    @Override
    public void processOrder(Order order)
    {
        productService.checkOrder(order);
    }
}
