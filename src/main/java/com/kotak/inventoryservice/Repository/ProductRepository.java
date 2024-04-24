package com.kotak.inventoryservice.Repository;

import com.kotak.inventoryservice.Dao.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.*;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class ProductRepository {
    private final DynamoDbTable<Product> table;

    public List<Product> getAll() {
        return table.scan().items().stream().filter(Product::isAvailable).toList();
    }

    public Product getById(String id) {
        return table.getItem(Key.builder().partitionValue(id).build());
    }

    public Product create(Product product) {
        return table.updateItem(product);
    }

    public void update(Product p1) {
        table.updateItem(p1);
    }

    public List<Product> getProducts(List<String> ids) {
        return table.scan().items().stream().filter((product -> ids.contains(product.getId()))).toList();
    }

    public void updateProductsQuantity(List<Product> products) {
        var executor = taskExecutor();
        products.forEach(product -> {
            executor.execute(() ->
                    table.putItem(product)
            );
        });
    }

    public void delete(Product id) {

        table.updateItem(id);
    }

    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.initialize();
        return executor;
    }
}
