package com.kotak.inventoryservice.Repository;

import com.kotak.inventoryservice.Dao.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderRepository {
    private final DynamoDbTable<Order> table;

    public List<Order> getAll() {
        return table.scan().items().stream().toList();
    }
    public Order getById(String id) {
        return table.getItem(Key.builder().partitionValue(id).build());
    }
    public void add(Order p1) {table.putItem(p1);}
    public void updateStatus(Order order) {table.putItem(order);}
}
