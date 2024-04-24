package com.kotak.inventoryservice.Services;

import com.kotak.inventoryservice.Dao.Order;
import com.kotak.inventoryservice.Enums.OrderStatus;
import com.kotak.inventoryservice.Repository.OrderRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


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
        template.send("orders", order.getId(), order );
        return repository.add(order);
    }

    public void processOrder(Order order) {
        order.setStatus(OrderStatus.COMPLETED.getValue());
        repository.updateStatus(order);
        template.send("orders", order.getId(), order);
    }

    public void cancelOrder(String id) {
        Order order = repository.getById(id);
        order.setStatus(OrderStatus.CANCELED.getValue());
        template.send("orders", order.getId(), order);
    }

    public void confirmCancellation(Order order) {
        repository.updateStatus(order);
    }

    public void rejectOrder(Order order) {
        order.setStatus(OrderStatus.REJECTED.getValue());
        repository.updateStatus(order);
    }
}
