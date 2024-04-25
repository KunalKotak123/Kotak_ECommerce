package com.kotak.inventoryservice.Services;

import com.kotak.inventoryservice.Dao.Order;
import com.kotak.inventoryservice.Enums.OrderStatus;
import com.kotak.inventoryservice.Exception.UnknownOrderStatusException;
import com.kotak.inventoryservice.Repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository repository;
    private final KafkaTemplate<String, Order> template;

    public OrderService(OrderRepository repository, KafkaTemplate<String, Order> template) {
        this.repository = repository;
        this.template = template;
    }

    public List<Order> getAll() {
        return repository.getAll();
    }

    public Order add(Order order) {
        try {
            var createdOrder = repository.add(order);
            template.send("orders", order.getId(), createdOrder);
            return createdOrder;
        } catch (Exception ex) {
            throw new UnknownOrderStatusException("Failed to create order");
        }
    }

    public void processOrder(Order order) {
        order.setStatus(OrderStatus.COMPLETED.getValue());
        repository.updateStatus(order);
        log.info("[Trace Order] Order completed successfully " + order);
        template.send("orders", order.getId(), order);
    }

    public void cancelOrder(String id) {
        Order order = repository.getById(id);
        if (order.getStatus().equals(OrderStatus.CANCELED.getValue())) {
            log.error("[Trace Order] Order is already cancelled for the id: " + id);
            throw new UnknownOrderStatusException("Order is already cancelled");
        }
        order.setStatus(OrderStatus.CANCELED.getValue());
        template.send("orders", order.getId(), order);
    }

    public void confirmCancellation(Order order) {
        repository.updateStatus(order);
        log.info("[Cancel Order] Order cancellation request completed " + order);
    }

    public void rejectOrder(Order order) {
        order.setStatus(OrderStatus.REJECTED.getValue());
        repository.updateStatus(order);
        log.error("[Trace Order] Order rejected and updated status in DB " + order );
    }
}
