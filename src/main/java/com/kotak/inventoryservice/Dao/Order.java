package com.kotak.inventoryservice.Dao;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.kotak.inventoryservice.Enums.OrderStatus;
import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@DynamoDbBean
@Data
public class Order {
    public static final String TABLE_NAME = "order";

    private String id;
    private BigDecimal price;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
    private List<ProductDetails> products;


    @DynamoDbPartitionKey
    @DynamoDBAutoGeneratedKey
    public String getId() {
        return UUID.randomUUID().toString();
    }

    @DynamoDBAttribute(attributeName = "price")
    public BigDecimal getPrice() {
        return price;
    }

    @DynamoDBAttribute(attributeName = "products")
    public List<ProductDetails> getProducts() {
        return products;
    }

    @DynamoDBAttribute(attributeName = "status")
    public String getStatus() {
        return OrderStatus.PENDING.getValue();
    }

    @DynamoDBAutoGeneratedTimestamp(strategy= DynamoDBAutoGenerateStrategy.ALWAYS)
    public String getUpdatedAt()
    {
        return getTime();
    }

    @DynamoDBAutoGeneratedTimestamp(strategy=DynamoDBAutoGenerateStrategy.CREATE)
    public String getCreatedAt() {return getTime();}

    private static String getTime() {
        return LocalDateTime.now().toString();
    }

}

