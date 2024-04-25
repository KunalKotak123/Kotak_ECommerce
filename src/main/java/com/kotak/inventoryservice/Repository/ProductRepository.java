package com.kotak.inventoryservice.Repository;

import com.kotak.inventoryservice.Dao.Product;
import com.kotak.inventoryservice.Dao.ProductList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.*;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
@Slf4j
@Component
@RequiredArgsConstructor
public class ProductRepository {
    private final DynamoDbTable<Product> table;
    @Cacheable(cacheNames = "products")
    public ProductList getAll() {
        var lstProducts = table.scan().items().stream().filter(Product::isAvailable).toList();
        return new ProductList(lstProducts);
    }
    @Cacheable(cacheNames = "product", key = "#id", unless = "#result == null")
    public Product getById(String id) {
        return table.getItem(Key.builder().partitionValue(id).build());
    }
    @CacheEvict(cacheNames = "products", allEntries = true)
    public Product create(Product product) {
        return table.updateItem(product);
    }
    @CacheEvict(cacheNames = "products", allEntries = true)
    public Product update(Product product) {
        return table.updateItem(p -> p.item(product).ignoreNulls(true));
    }

    public List<Product> getProducts(List<String> ids) {
        return table.scan().items().stream().filter((product -> ids.contains(product.getId()))).toList();
    }
    @CacheEvict(cacheNames = "products", allEntries = true)
    public void updateProductsQuantity(List<Product> products) {
        var executor = taskExecutor();
        products.forEach(product -> {
            executor.execute(() ->
                    table.putItem(product)
            );
        });
    }
    @Caching(evict = { @CacheEvict(cacheNames = "product", key = "#id"),
            @CacheEvict(cacheNames = "products", allEntries = true) })
    public void delete(String id) {
        var product = getById(id);
        product.setAvailable(false);
        update(product);
    }

    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.initialize();
        return executor;
    }
}
