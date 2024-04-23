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
}
