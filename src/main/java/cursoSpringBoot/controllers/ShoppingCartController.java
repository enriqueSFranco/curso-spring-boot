package cursoSpringBoot.controllers;

import cursoSpringBoot.domain.Product;
import cursoSpringBoot.dto.ApiResponse;
import cursoSpringBoot.services.ShoppingCartService;
import cursoSpringBoot.utils.BeanNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/products")
public class ShoppingCartController {
    private final Logger logger = Logger.getLogger(ShoppingCartController.class.getName());
    // inyeccion de dependencia por campo
    @Autowired
    @Qualifier(BeanNames.JSON_RESOURCE_SERVICE)
    private ShoppingCartService service; // composicion OOP, referencia de un objeto desde los atributos de una clase

    public ShoppingCartController(ShoppingCartService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getShoppingCart() {
        List<Product> shoppingCart = Optional
                .ofNullable(this.service.getShoppingCart())
                .orElse(Collections.emptyList());

        if (shoppingCart.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(shoppingCart, "Carrito recuperado exitosamente"));
    }

}
