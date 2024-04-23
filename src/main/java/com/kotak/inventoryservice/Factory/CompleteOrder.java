package com.kotak.inventoryservice.Factory;


import com.kotak.inventoryservice.Dao.Order;
import com.kotak.inventoryservice.Exception.ProcessOrder;
import com.kotak.inventoryservice.Services.ProductService;

public class CompleteOrder implements ProcessOrder {
    private final ProductService productService;
    public CompleteOrder(ProductService productService) {
        this.productService = productService;
    }
    @Override
    public void processOrder(Order order)
    {
        productService.completeOrder(order);
    }
}
