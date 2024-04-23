package com.kotak.inventoryservice.Dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDBDocument
@DynamoDbBean
public class ProductDetails
{
    public void setId(String id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @DynamoDBAttribute(attributeName = "id")
    public String getId() {
        return id;
    }
    @DynamoDBAttribute(attributeName = "quantity")
    public int getQuantity() {
        return quantity;
    }


    private String id;
    private int quantity;
}
