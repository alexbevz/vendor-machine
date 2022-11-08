package ru.bevz.vendor_machine.simple_model;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import ru.bevz.vendor_machine.component.Inventory;
import ru.bevz.vendor_machine.component.Product;

public class ProductInventory implements Inventory {

    private final HashMap<Product, AtomicInteger> productQuantityMap = new HashMap<>();

    @Override
    public void addProduct(Product product) {
        if (hasProduct(product)) {
            productQuantityMap.get(product).incrementAndGet();
        } else {
            productQuantityMap.put(product, new AtomicInteger(1));
        }
    }

    @Override
    public Product dropProduct(Product product) {
        if (hasProduct(product)) {
            productQuantityMap.get(product).decrementAndGet();
        } else {
            throw new NotContainProductException();
        }
        return product;
    }

    @Override
    public boolean hasProduct(Product product) {
        return productQuantityMap.containsKey(product);
    }

    @Override
    public int getQuantityProduct(Product product) {
        return Optional.of(productQuantityMap.get(product))
            .orElse(new AtomicInteger(0)).get();
    }

    @Override
    public List<Product> getProducts() {
        return productQuantityMap.keySet().stream().toList();
    }
}
