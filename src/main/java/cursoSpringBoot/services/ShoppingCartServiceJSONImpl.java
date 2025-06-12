package cursoSpringBoot.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cursoSpringBoot.domain.Product;
import cursoSpringBoot.utils.BeanNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

//@Primary // establecemos el bean de servicio 'ShoppingCartServiceJSONImpl' como servicio primario
@Service(BeanNames.JSON_RESOURCE_SERVICE)
public class ShoppingCartServiceJSONImpl implements ShoppingCartService {
    private final ObjectMapper objectMapper;

    // inyeccion de dependencia en constructor
    @Autowired
    public ShoppingCartServiceJSONImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Product> getShoppingCart() {
        try(InputStream is = this.getClass().getResourceAsStream("/products.json")) {
            return this.objectMapper.readValue(is, new TypeReference<List<Product>>() {});
            //products = new ObjectMapper() // Crea una instancia de Jackson para leer/escribir JSON.
            //                    .readValue(this.getClass().getResourceAsStream("/products.json"), new TypeReference<List<Product>>() {});

            // this.getClass().getResourceAsStream("/products.json") → Carga el archivo products.json desde el classpath (por ejemplo, src/main/resources).
            // new TypeReference<List<Product>>() {} → Le indica a Jackson que debe leer una lista de
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getProductByName(String name) {}

    @Override
    public List<Product> getProductByPrice(Double price) {}
}
