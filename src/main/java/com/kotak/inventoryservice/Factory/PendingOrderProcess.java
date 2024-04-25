package com.kotak.inventoryservice.Factory;

import com.kotak.inventoryservice.Dao.Order;
import com.kotak.inventoryservice.Services.ProductService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PendingOrderProcess implements ProcessOrder {
    private final ProductService productService;

    public PendingOrderProcess(ProductService productService) {
        this.productService = productService;
    }
    @Override
    public void processOrder(Order order)
    {
        log.info("[Trace Order]  Check Order " + order);
        productService.checkOrder(order);
    }
}
