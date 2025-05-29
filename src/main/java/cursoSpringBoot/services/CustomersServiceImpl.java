package cursoSpringBoot.services;

import cursoSpringBoot.domain.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomersServiceImpl {
    private List<Customer> customers = new ArrayList<>(List.of(
            new Customer(1, "Alice", "alice123", "pass1"),
            new Customer(2, "Bob", "bob456", "pass2"),
            new Customer(3, "Charlie", "charlie789", "pass3"),
            new Customer(4, "Diana", "diana321", "pass4"),
            new Customer(5, "Ethan", "ethan654", "pass5")
    ));

    public List<Customer> getCustomers() {
        return this.customers;
    }
}
