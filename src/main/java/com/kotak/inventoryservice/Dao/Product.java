package com.kotak.inventoryservice.Dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.xspec.NULL;
import lombok.Data;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.UpdateBehavior;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbUpdateBehavior;

import java.util.UUID;

@DynamoDbBean
@Data
public class Product {
    public static final String TABLE_NAME = "product";

    private String id;
    private String name;
    private Float price;
    private Integer quantity;
    @Setter
    private Integer reserveQuantity;
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
    @DynamoDbUpdateBehavior(UpdateBehavior.WRITE_ALWAYS)
    public Float getPrice() {
        return price;
    }

    @DynamoDBAttribute(attributeName = "quantity")
    @DynamoDbUpdateBehavior(UpdateBehavior.WRITE_ALWAYS)
    public Integer getQuantity() {
        return quantity;
    }
    @DynamoDBAttribute(attributeName = "reserveQuantity")
    @DynamoDbUpdateBehavior(UpdateBehavior.WRITE_ALWAYS)
    public Integer getReserveQuantity() {
        return reserveQuantity;
    }

    public void setQuantity(Integer quantity) {
        if (quantity >= 0) {
            this.quantity = quantity;
        } else {
            throw new IllegalArgumentException("Quantity must be a positive integer.");
        }
    }

}