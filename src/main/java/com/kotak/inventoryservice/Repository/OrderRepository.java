package com.kotak.inventoryservice.Repository;

import com.kotak.inventoryservice.Dao.Order;
import com.kotak.inventoryservice.Dao.Product;
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
    public void add(Order p1) {table.putItem(p1);}

}
