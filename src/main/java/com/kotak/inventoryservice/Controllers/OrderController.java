package com.kotak.inventoryservice.Controllers;

import com.kotak.inventoryservice.Dao.Order;
import com.kotak.inventoryservice.Exception.UnknownOrderStatusException;
import com.kotak.inventoryservice.Response.ResponseHandler;
import com.kotak.inventoryservice.Services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@EnableKafka
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        List<Order> orders = service.getAll();
        return ResponseHandler.sendResponse("Successfully fetched all orders", HttpStatus.OK, orders);
    }

    @PostMapping
    public ResponseEntity<Object> createOrder(@RequestBody Order order) {
        try {
            log.info("[Trace Order] Order Create request received " + order);
            Order newOrder = service.add(order);
            var orderData = new HashMap<>();
            var orderDetails = new HashMap<>();
            orderDetails.put("id", newOrder.getId());
            orderDetails.put("status", newOrder.getStatus());
            orderData.put("order_details", orderDetails);

            return ResponseHandler.sendResponse("Successfully initiated the order", HttpStatus.OK, orderData);
        } catch (Exception ex) {
            throw new UnknownOrderStatusException("Failed to create an order");
        }
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<Object> cancelOrder(@PathVariable String id) {
        log.info("[Trace Order] Cancel order request received for Order id: " + id);
        service.cancelOrder(id);
        return ResponseHandler.sendResponse("Successfully initiated order cancellation", HttpStatus.OK);
    }

}

