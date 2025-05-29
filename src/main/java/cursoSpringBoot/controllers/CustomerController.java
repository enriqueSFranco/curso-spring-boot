package cursoSpringBoot.controllers;

import cursoSpringBoot.domain.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private List<Customer> customers = new ArrayList<>(List.of(
            new Customer(1, "Alice", "alice123", "pass1"),
            new Customer(2, "Bob", "bob456", "pass2"),
            new Customer(3, "Charlie", "charlie789", "pass3"),
            new Customer(4, "Diana", "diana321", "pass4"),
            new Customer(5, "Ethan", "ethan654", "pass5")
    ));

    @RequestMapping(method = RequestMethod.GET)
    // @GetMapping
    public ResponseEntity<List<Customer>> getCustomers() {
        if (this.customers.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204 No Content
        }
        return ResponseEntity.ok(customers); // HTTP 200 OK con la lista
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    //@GetMapping("/{name}")
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer postCustomer(@RequestBody Customer customer) {
        this.customers.add(customer);
        return customer;
    }


    @PutMapping
    public ResponseEntity<Customer> putCustomer(@RequestBody Customer updatedCustomer) {
        for (Customer c : customers) {
            if (c.getID() == updatedCustomer.getID()) {
                c.setName(updatedCustomer.getName());
                c.setUsername(updatedCustomer.getUsername());
                c.setPassword(updatedCustomer.getPassword());
                return ResponseEntity.ok(c);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping
    public ResponseEntity<Customer> patchCustomer(@RequestBody Customer patch) {
        for (Customer c : customers) {
            if (c.getID() == patch.getID()) {
                if (patch.getName() != null) c.setName(patch.getName());
                if (patch.getUsername() != null) c.setUsername(patch.getUsername());
                if (patch.getPassword() != null) c.setPassword(patch.getPassword());
                return ResponseEntity.ok(c);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable int id) {
        for (Customer c : customers) {
            if (c.getID() == id) {
                customers.remove(c);
                return ResponseEntity.ok(c);
            }
        }
        return ResponseEntity.notFound().build();
    }
}
