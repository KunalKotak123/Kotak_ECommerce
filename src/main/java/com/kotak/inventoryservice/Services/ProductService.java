package com.kotak.inventoryservice.Services;

import com.kotak.inventoryservice.Dao.Order;
import com.kotak.inventoryservice.Dao.Product;
import com.kotak.inventoryservice.Dao.ProductDetails;
import com.kotak.inventoryservice.Dao.ProductList;
import com.kotak.inventoryservice.Enums.OrderStatus;
import com.kotak.inventoryservice.Repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository repository;

    private final KafkaTemplate<String, Order> template;

    public ProductService(ProductRepository repository, KafkaTemplate<String, Order> template) {
        this.repository = repository;
        this.template = template;
    }

    public ProductList getAll() {
        return repository.getAll();
    }

    public Product getProductById(String id) {
        return repository.getById(id);
    }

    public Product createProduct(Product product) {
        return repository.create(product);
    }

    public Product update(Product p1) {
        return repository.update(p1);
    }

    public void deleteProduct(String id) {
        repository.delete(id);
    }


    public void checkOrder(Order order) {
        var pids = order.getProducts().stream().map(ProductDetails::getId).toList();
        var orderProducts = repository.getProducts(pids);
        if (validateOrder(orderProducts, order)) {
            orderProducts.forEach(product -> {
                var requiredQty = order.getProducts().stream().filter(x -> x.getId().equals(product.getId())).findFirst().get().getQuantity();
                product.setReserveQuantity(requiredQty);
                var newQty = product.getQuantity() - requiredQty;
                product.setQuantity(newQty);
            });
            repository.updateProductsQuantity(orderProducts);
            order.setStatus(OrderStatus.ACCEPTED.getValue());
            log.info("[Trace Order] Order Accepted " + order);
            template.send("stock-orders", order.getId(), order);
        } else {
            order.setStatus(OrderStatus.REJECTED.getValue());
            log.info("[Trace Order] Order Rejected. Product(s) are unavailable " + order);
            template.send("stock-orders", order.getId(), order);
        }
    }

    private boolean validateOrder(List<Product> orderProducts, Order order) {
        AtomicBoolean hasQuantity = new AtomicBoolean(true);

        if (orderProducts.size() == order.getProducts().size())
            orderProducts.forEach((product -> {
                var requiredQty = order.getProducts().stream().filter(x -> x.getId().equals(product.getId())).findFirst().get().getQuantity();
                if (product.getQuantity() <= requiredQty) {
                    log.info("[Trace Order] Order Rejected. [ "+product.getName()+" ] has not have enough quantity " + order);
                    hasQuantity.set(false);
                }
            }));
        else {
            hasQuantity.set(false);
        }
        return hasQuantity.get();
    }

    public void cancelOrder(Order order) {
        var pids = order.getProducts().stream().map(ProductDetails::getId).toList();
        var orderProducts = repository.getProducts(pids);
        orderProducts.forEach(product -> {
            var requiredQty = order.getProducts().stream().filter(x -> x.getId().equals(product.getId())).findFirst().get().getQuantity();
            product.setQuantity(product.getQuantity() + requiredQty);
        });
        repository.updateProductsQuantity(orderProducts);
        log.info("[Cancel Order] Successfully updated inventory with canceled order quantity " + order);
        template.send("stock-orders", order.getId(), order);
    }

    public void completeOrder(Order order) {
        var pids = order.getProducts().stream().map(ProductDetails::getId).toList();
        var orderProducts = repository.getProducts(pids);
        orderProducts.forEach(product -> {
            var requiredQty = order.getProducts().stream().filter(x -> x.getId().equals(product.getId())).findFirst().get().getQuantity();
            product.setReserveQuantity(product.getReserveQuantity() - requiredQty);
        });
        repository.updateProductsQuantity(orderProducts);
        log.info("[Trace Order] Updated inventory quantity successfully " + order);
        // Todo : At this point send notification to user to notify that the order completed successfully
    }
}
