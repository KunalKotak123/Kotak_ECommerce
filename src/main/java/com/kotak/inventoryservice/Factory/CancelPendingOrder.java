package com.kotak.inventoryservice.Factory;

import com.kotak.inventoryservice.Dao.Order;
import com.kotak.inventoryservice.Dao.Product;
import com.kotak.inventoryservice.Exception.ProcessOrder;
import com.kotak.inventoryservice.Services.OrderService;
import com.kotak.inventoryservice.Services.ProductService;

public class CancelPendingOrder implements ProcessOrder {
    private final ProductService productService;
    public CancelPendingOrder(ProductService productService)
    {
        this.productService = productService;
    }
    @Override
    public void processOrder(Order order) {
        productService.cancelOrder(order);
    }
}
