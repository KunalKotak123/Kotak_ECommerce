package com.kotak.inventoryservice.Repository;

import com.kotak.inventoryservice.Dao.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductRepository {
  private final DynamoDbTable<Product> table;

    public List<Product> getAll() {
        return table.scan().items().stream().filter(Product::isAvailable).toList();
    }
    public Product getById(String id) {return table.getItem(Key.builder().partitionValue(id).build());}
    public void create(Product product) {table.putItem(product);}
    public void update(Product p1) {table.putItem(p1);}
    public void delete(Product id) {table.updateItem(id);}
}
