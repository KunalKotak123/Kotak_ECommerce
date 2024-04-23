package com.kotak.inventoryservice.Config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.DYNAMODB;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
@Slf4j
@Configuration
public class ContainerConfig {


    @Bean
    @ServiceConnection
    public KafkaContainer kafkaContainer() {
        return new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.0"))
                .withKraft()
                .withReuse(true);
    }
    @Bean
    public LocalStackContainer localStack() {

        var ls = new LocalStackContainer(DockerImageName.parse("localstack/localstack:latest"))
                .withServices(DYNAMODB);
        configureForReuse(ls);
        System.setProperty(
                "aws.dynamodb.endpointOverride",
                ls.getEndpointOverride(DYNAMODB).toString());
        System.out.println("[CONFIG] AWS Access Key: " + ls.getAccessKey());
        System.out.println("[CONFIG] AWS Secret Key: " + ls.getSecretKey());
        System.out.println("[CONFIG] DynamoDB endpoint: " + ls.getEndpointOverride(DYNAMODB));
        return ls;
    }
    @Primary
    @Bean
    public int dependencyPlaceholder(LocalStackContainer localStack) {
        return -1;
    }
    private static <T extends GenericContainer<T>> void configureForReuse(GenericContainer<T> container) {
        container.withReuse(true);
        if (!container.isRunning()) {
            container.start();
        }
    }
}
