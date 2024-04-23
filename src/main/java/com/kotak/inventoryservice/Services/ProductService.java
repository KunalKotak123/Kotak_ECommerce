package com.kotak.inventoryservice.Services;

import com.kotak.inventoryservice.Dao.Order;
import com.kotak.inventoryservice.Dao.Product;
import com.kotak.inventoryservice.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.eclipse.jetty.client.CompletableResponseListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    private KafkaTemplate<String, Order> template;

    public List<Product> getAll() {
        return repository.getAll();
    }

    public Product getProductById(String id) { return repository.getById(id);}

    public void createProduct(Product product) {repository.create(product);}

    public void update(Product p1) {repository.update(p1);}

    public void deleteProduct(String id) {

        Product product = new Product();
        product.setId(id);
        product.setAvailable(false);

        repository.delete(product);
    }


    public void checkOrder(Order order)
    {
        // check if quantity is available. Check in redis
        template.send("orders", order.getId(), order );
        // put in kafka Accept / Reject
    }

    public void cancelOrder(Order order)
    {
        // bump up the quantity. Update in both dynamoDB and redis.
    }

    public void completeOrder(Order order)
    {
        // decrease reserved quantity in redis and ddb.
        // Update order status as Completed in orders table
    }
}
