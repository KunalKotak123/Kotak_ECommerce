package com.kotak.inventoryservice.Factory;

import com.kotak.inventoryservice.Exception.ProcessOrder;
import com.kotak.inventoryservice.Factory.CancelPendingOrder;
import com.kotak.inventoryservice.Factory.PendingOrderProcess;
import com.kotak.inventoryservice.Services.OrderService;
import com.kotak.inventoryservice.Services.ProductService;

public class OrderProcessingFactory
{

    public static ProcessOrder getOrderProcessor(String status, ProductService productService)
    {
        if(status.equals("Pending"))
        {
            return new PendingOrderProcess(productService);
        }
            return new CancelPendingOrder(productService);

    }
}
