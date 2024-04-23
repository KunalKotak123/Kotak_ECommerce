package com.kotak.inventoryservice.Dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.UUID;

@DynamoDbBean
@Data
public class Product {
    public static final String TABLE_NAME = "product";

    private String id;
    private String name;
    private Float price;
    private int quantity;
    private boolean isAvailable;

    public Product() {
        this.id = UUID.randomUUID().toString();
        this.isAvailable = true;
    }

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    @DynamoDBAttribute(attributeName = "price")
    public Float getPrice() {
        return price;
    }

    @DynamoDBAttribute(attributeName = "quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity >= 0) {
            this.quantity = quantity;
        } else {
            throw new IllegalArgumentException("Quantity must be a positive integer.");
        }
    }

}