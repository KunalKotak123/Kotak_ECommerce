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
        return table.scan().items().stream().toList();
    }
    public Product getProductById(String id) {return table.getItem(Key.builder().partitionValue(id).build());}
    public void create(Product product) {table.putItem(product);}
    public void add(Product p1) {table.putItem(p1);}
}
