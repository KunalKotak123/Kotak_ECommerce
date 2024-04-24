package com.kotak.inventoryservice.Services;

import com.kotak.inventoryservice.Dao.Order;
import com.kotak.inventoryservice.Dao.Product;
import com.kotak.inventoryservice.Dao.ProductDetails;
import com.kotak.inventoryservice.Dao.ProductList;
import com.kotak.inventoryservice.Enums.OrderStatus;
import com.kotak.inventoryservice.Repository.ProductRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


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

    public Product getProductById(String id) { return repository.getById(id);}

    public void createProduct(Product product) {repository.create(product);}

    public void update(Product p1) {repository.update(p1);}

    public void deleteProduct(String id) {
        repository.delete(id);
    }


    public void checkOrder(Order order)
    {
        var pids = order.getProducts().stream().map(ProductDetails::getId).toList();
        var orderProducts = repository.getProducts(pids);
        if(validateOrder(orderProducts, order)){
            orderProducts.forEach(product -> {
                var requiredQty = order.getProducts().stream().filter(x->x.getId().equals(product.getId())).findFirst().get().getQuantity();
                product.setReserveQuantity(requiredQty);
                var newQty = product.getQuantity() - requiredQty;
                product.setQuantity(newQty);
            });
            repository.updateProductsQuantity(orderProducts);
            order.setStatus(OrderStatus.ACCEPTED.getValue());
            template.send("stock-orders", order.getId(), order );
        }
        else{
            order.setStatus(OrderStatus.REJECTED.getValue());
            template.send("stock-orders", order.getId(), order );
        }
    }

    private boolean validateOrder(List<Product> orderProducts, Order order) {
        AtomicBoolean hasQuantity = new AtomicBoolean(true);

        orderProducts.forEach((product -> {
            var requiredQty = order.getProducts().stream().filter(x->x.getId().equals(product.getId())).findFirst().get().getQuantity();
            if(product.getQuantity() <=  requiredQty){
                hasQuantity.set(false);
            }
        }));
        return hasQuantity.get();
    }

    public void cancelOrder(Order order)
    {
        var pids = order.getProducts().stream().map(ProductDetails::getId).toList();
        var orderProducts = repository.getProducts(pids);
        orderProducts.forEach(product -> {
            var requiredQty = order.getProducts().stream().filter(x->x.getId().equals(product.getId())).findFirst().get().getQuantity();
            product.setQuantity(product.getQuantity() + requiredQty);
        });
        repository.updateProductsQuantity(orderProducts);
    }

    public void completeOrder(Order order)
    {
        var pids = order.getProducts().stream().map(ProductDetails::getId).toList();
        var orderProducts = repository.getProducts(pids);
            orderProducts.forEach(product -> {
                var requiredQty = order.getProducts().stream().filter(x->x.getId().equals(product.getId())).findFirst().get().getQuantity();
                product.setReserveQuantity(product.getReserveQuantity() - requiredQty);
            });
            repository.updateProductsQuantity(orderProducts);

            // Todo : At this point send notification to user to notify that the order completed successfully
    }
}
