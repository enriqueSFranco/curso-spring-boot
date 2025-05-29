package cursoSpringBoot.services;

import cursoSpringBoot.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> getShoppingCart();
    List<Product> getProductByName(String name);
    List<Product> getProductByPrice(Double price);
}
