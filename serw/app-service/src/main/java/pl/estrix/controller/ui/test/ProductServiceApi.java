package pl.estrix.controller.ui.test;

import java.util.List;

public interface ProductServiceApi {
    void save(Product product);
    List<Product> getAllProducts();
}