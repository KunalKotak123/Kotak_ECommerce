package com.kotak.inventoryservice.Exception;

import com.kotak.inventoryservice.Dao.Order;

public interface ProcessOrder
{
    void processOrder(Order order);
}
