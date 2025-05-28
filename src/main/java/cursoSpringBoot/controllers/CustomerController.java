package cursoSpringBoot.controllers;

import cursoSpringBoot.domain.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerController {

    private List<Customer> customers = new ArrayList<>(List.of(
            new Customer(1, "Alice", "alice123", "pass1"),
            new Customer(2, "Bob", "bob456", "pass2"),
            new Customer(3, "Charlie", "charlie789", "pass3"),
            new Customer(4, "Diana", "diana321", "pass4"),
            new Customer(5, "Ethan", "ethan654", "pass5")
    ));

    @GetMapping("/customers")
    public List<Customer> getCustomers() {
        return customers;
    }

    @GetMapping("/customers/{name}")
    private ResponseEntity<Customer> getCustomerByName(@PathVariable String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return customers.stream()
                .filter(c -> c.getName() != null && c.getName().equalsIgnoreCase(name.trim()))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer postCustomer(@RequestBody Customer customer) {
        this.customers.add(customer);
        return customer;
    }
}
