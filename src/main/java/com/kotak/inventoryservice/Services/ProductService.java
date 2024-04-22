package com.kotak.inventoryservice.Services;

import com.kotak.inventoryservice.Dao.Product;
import com.kotak.inventoryservice.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public List<Product> getAll() {
        return repository.getAll();
    }

    public void add(Product p1)
    {
        repository.add(p1);
    }
}
