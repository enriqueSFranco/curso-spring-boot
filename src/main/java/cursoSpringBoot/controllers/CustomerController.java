package cursoSpringBoot.controllers;

import cursoSpringBoot.domain.Customer;
import cursoSpringBoot.dto.ApiResponse;
import cursoSpringBoot.services.CustomersServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomersServiceImpl serviceCustomers = new CustomersServiceImpl();

    @RequestMapping(method = RequestMethod.GET)
    // @GetMapping
    public ResponseEntity<List<Customer>> getCustomers() {
        List<Customer> customers = Optional
                .ofNullable(this.serviceCustomers.getCustomers())
                .orElse(Collections.emptyList());
        if (customers.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204 No Content
        }
        return ResponseEntity.ok(customers); // HTTP 200 OK con la lista
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    //@GetMapping("/{name}")
    private ResponseEntity<ApiResponse<Customer>> getCustomerByName(@PathVariable String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, "El nombre no puede estar vacío"));
        }
        return this.serviceCustomers.getCustomers().stream()
                .filter(c -> c.getName() != null && c.getName().equalsIgnoreCase(name.trim()))
                .findFirst()
                .map(c -> ResponseEntity.ok(new ApiResponse<>(c, "Cliente encontrado")))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(null, "El cliente " + name + " no se encontro")));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<Customer>> postCustomer(@RequestBody Customer customer) {
        this.serviceCustomers.getCustomers().add(customer);
        // agregando la URI a la respuesta http
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest() // obtiene la informacion de la uri
                .path("/{name}") // agrega un segmento a la uri base
                .buildAndExpand(customer.getName())
                .toUri();

        return ResponseEntity.created(location).body(new ApiResponse<>(customer, "El cliente" + customer.getName() +" fue creado exitosamente"));

        // return ResponseEntity.created(location).build();
//        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(customer, "El cliente" + customer.getName() +" fue creado exitosamente"));
    }


    @PutMapping
    public ResponseEntity<ApiResponse<Customer>> putCustomer(@RequestBody Customer updatedCustomer) {
        for (Customer c : this.serviceCustomers.getCustomers()) {
            if (c.getID() == updatedCustomer.getID()) {
                c.setName(updatedCustomer.getName());
                c.setUsername(updatedCustomer.getUsername());
                c.setPassword(updatedCustomer.getPassword());
                return ResponseEntity.noContent().build(); // respuesta simplificada sin mensaje personaliado
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<Customer>> patchCustomer(@RequestBody Customer patch) {
        for (Customer c : this.serviceCustomers.getCustomers()) {
            if (c.getID() == patch.getID()) {
                if (patch.getName() != null) c.setName(patch.getName());
                if (patch.getUsername() != null) c.setUsername(patch.getUsername());
                if (patch.getPassword() != null) c.setPassword(patch.getPassword());
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(null, "El cliente " + patch.getName() + " no se encontro"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> deleteCustomer(@PathVariable int id) {
        for (Customer c : this.serviceCustomers.getCustomers()) {
            if (c.getID() == id) {
                this.serviceCustomers.getCustomers().remove(c);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse<>(c, "Cliente eliminado correctamente"));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(null, "El cliente con ID=" + id + " no se encontro"));
    }
}
