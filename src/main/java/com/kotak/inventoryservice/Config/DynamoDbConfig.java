package com.kotak.inventoryservice.Config;

import java.net.URI;

import com.kotak.inventoryservice.Dao.Order;
import com.kotak.inventoryservice.Dao.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.extensions.VersionedRecordExtension;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;


@Configuration
@Slf4j
public class DynamoDbConfig {
    @Value("${aws.dynamodb.endpointOverride}")
    private String dynamodbEndpoint;
    @Value("${region}")
    private String region;
    public DynamoDbConfig()
    {
        System.out.println("I am here");
    }
    @Bean
    @DependsOn("dependencyPlaceholder")
    public DynamoDbClient getDynamoDbClient() {
        var builder = DynamoDbClient
                .builder()
                .credentialsProvider(DefaultCredentialsProvider.create());

        if (dynamodbEndpoint != null && !dynamodbEndpoint.isBlank()) {
            builder.region(Region.of(region))
                    .endpointOverride(URI.create(dynamodbEndpoint));
            log.info("DynamoDB Client initialized in region " + region);
            log.warn("DynamoDB Client ENDPOINT overridden to " + dynamodbEndpoint);
        }
        return builder.build();
    }

    @Bean
    public DynamoDbEnhancedClient getDynamoDbEnhancedClient(DynamoDbClient ddbc) {
        return DynamoDbEnhancedClient
                .builder()
                .extensions(VersionedRecordExtension.builder().build())
                .dynamoDbClient(ddbc)
                .build();
    }

    @Bean
    public DynamoDbTable<Product> getProductTable(DynamoDbEnhancedClient dbClient) {
        return dbClient.table(Product.TABLE_NAME, TableSchema.fromBean(Product.class));
    }
    @Bean
    public DynamoDbTable<Order> getOrderLocaleTable(DynamoDbEnhancedClient dbClient) {
        return dbClient.table(Order.TABLE_NAME, TableSchema.fromBean(Order.class));
    }
}