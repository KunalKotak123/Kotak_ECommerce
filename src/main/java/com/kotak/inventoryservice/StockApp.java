//package com.kotak.inventoryservice;
//
//import com.kotak.inventoryservice.Services.ProductService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.kafka.annotation.EnableKafkaStreams;
//import org.springframework.kafka.config.TopicBuilder;
//import org.springframework.scheduling.annotation.EnableAsync;
//@Slf4j
//@SpringBootApplication
//@EnableKafkaStreams
//@EnableAsync
//public class StockApp {
//
//    public static void main(String[] args) {
//        SpringApplication.run(StockApp.class, args);
//    }
//
//    @Bean
//    public NewTopic orders() {
//        return TopicBuilder.name("orders")
//                .partitions(3)
//                .compact()
//                .build();
//    }
//
//    @Bean
//    public NewTopic stockTopic() {
//        return TopicBuilder.name("stock-orders")
//                .partitions(3)
//                .compact()
//                .build();
//    }
//
//    @Autowired
//    ProductService productService;
//
////    @Bean
////    public KStream<Long, Order> stream(StreamsBuilder builder) {
////        JsonSerde<Order> orderSerde = new JsonSerde<>(Order.class);
////        KStream<Long, Order> stream = builder
////                .stream("payment-orders", Consumed.with(Serdes.Long(), orderSerde));
////
////        stream.join(
////                        builder.stream("stock-orders"),
////                        orderManageService::confirm,
////                        JoinWindows.of(Duration.ofSeconds(10)),
////                        StreamJoined.with(Serdes.Long(), orderSerde, orderSerde))
////                .peek((k, o) -> LOG.info("Output: {}", o))
////                .to("orders");
////
////        return stream;
////    }
////
////    @Bean
////    public KTable<Long, Order> table(StreamsBuilder builder) {
////        KeyValueBytesStoreSupplier store =
////                Stores.persistentKeyValueStore("orders");
////        JsonSerde<Order> orderSerde = new JsonSerde<>(Order.class);
////        KStream<Long, Order> stream = builder
////                .stream("orders", Consumed.with(Serdes.Long(), orderSerde));
////        return stream.toTable(Materialized.<Long, Order>as(store)
////                .withKeySerde(Serdes.Long())
////                .withValueSerde(orderSerde));
////    }
////
////    @Bean
////    public Executor taskExecutor() {
////        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
////        executor.setCorePoolSize(5);
////        executor.setMaxPoolSize(5);
////        executor.setThreadNamePrefix("kafkaSender-");
////        executor.initialize();
////        return executor;
////    }
//}
