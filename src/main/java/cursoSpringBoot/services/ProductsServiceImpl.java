package cursoSpringBoot.services;

import cursoSpringBoot.domain.Product;
import cursoSpringBoot.utils.TextUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


@Service
public class ProductsServiceImpl implements ProductService {
    private final static double DEFAULT_TOLERANCE = 0.001;
    private List<Product> shoppingCart = new ArrayList<>(List.of(
            new Product("Laptop", 1200.0, 5),
            new Product("Smartphone", 800.0, 10),
            new Product("Headphones", 150.0, 20)
    ));

    @Override
    public List<Product> getShoppingCart() {
        return this.shoppingCart;
    }

    @Override
    public List<Product> getProductByName(String name) {
        if (name == null || name.isBlank()) {
            return List.of();
        }

        String normalized = TextUtils.normalizeText(name);

        Pattern pattern = Pattern.compile(Pattern.quote(normalized), Pattern.CASE_INSENSITIVE);

        return this.shoppingCart.stream()
                .filter(p -> {
                    String productName = TextUtils.normalizeText(p.getName());
                    return productName != null && pattern.matcher(productName).find();
                })
                .toList();
    }

    @Override
    public List<Product> getProductByPrice(Double price) {

        if (price == null) {
            return List.of();
        }
        return this.getProductByPrice(price, DEFAULT_TOLERANCE);

    }

    private List<Product> getProductByPrice(Double price, double tolerance) {

        if (tolerance < 0) {
            throw new IllegalArgumentException("La tolerancia no puede ser negativa.");
        }

        return this.shoppingCart.stream()
                .filter(p -> p.getPrice() != null && Math.abs(p.getPrice() - price) <= tolerance)
                .toList();
    }
}
