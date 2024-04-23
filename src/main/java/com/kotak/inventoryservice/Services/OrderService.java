package com.kotak.inventoryservice.Services;

import com.kotak.inventoryservice.Dao.Order;
import com.kotak.inventoryservice.Dao.Product;
import com.kotak.inventoryservice.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;

    public List<Order> getAll() {
        return repository.getAll();
    }

    //public Product getProductById(String id) { return repository.getProductById(id);}

    //public void createProduct(Product product) {repository.create(product);}

    public void add(Order p1) {repository.add(p1);}

    public void processOrder(Order order)
    {
        // update database order table with status completed
        // send message in kafka saying completed.
    }

    public void cancelOrder(Order order)
    {
        // update the order status in DDB.
    }
}
