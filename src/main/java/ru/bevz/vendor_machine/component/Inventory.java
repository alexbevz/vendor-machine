package ru.bevz.vendor_machine.component;

import java.util.List;

public interface Inventory {

    void addProduct(Product product);

    Product dropProduct(Product product);

    boolean hasProduct(Product product);

    int getQuantityProduct(Product product);

    List<Product> getProducts();
}
