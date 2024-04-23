package com.kotak.inventoryservice.Factory;

import com.kotak.inventoryservice.Enums.OrderStatus;
import com.kotak.inventoryservice.Exception.UnknownOrderStatusException;
import com.kotak.inventoryservice.Services.ProductService;
import lombok.SneakyThrows;

public class OrderProcessingFactory
{

    @SneakyThrows
    public static ProcessOrder getOrderProcessor(OrderStatus status, ProductService productService)
    {
        return switch (status) {
            case OrderStatus.PENDING -> new PendingOrderProcess(productService);
            case OrderStatus.COMPLETED -> new CompleteOrder(productService);
            case OrderStatus.CANCELED -> new CancelPendingOrder(productService);
            default -> throw new UnknownOrderStatusException(String.format("Unknown Order Status found {}" ,status));
        };

    }
}
