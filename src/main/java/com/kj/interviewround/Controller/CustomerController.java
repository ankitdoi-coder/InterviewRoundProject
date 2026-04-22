package com.kj.interviewround.Controller;

import com.kj.interviewround.DTO.CustomerDTO;
import com.kj.interviewround.Entity.Customer;
import com.kj.interviewround.Services.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // POST - Create a new customer (201 Created) - accepts DTO with flat address fields
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer createdCustomer = customerService.createCustomerFromDTO(customerDTO);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    // GET - Get all customers page wise 
    @GetMapping
    public ResponseEntity<?> getAllCustomers(Pageable pageable) {
        Page<Customer> customers=customerService.getAllCustomers(pageable);
        // List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    // GET - Get customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    // PUT - Full update (replace all fields)
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetails) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerDetails);
        return ResponseEntity.ok(updatedCustomer);
    }

    // PATCH - Partial update (update only provided fields)
    @PatchMapping("/{id}")
    public ResponseEntity<Customer> partialUpdateCustomer(@PathVariable Long id, @RequestBody Customer customerDetails) {
        Customer updatedCustomer = customerService.partialUpdateCustomer(id, customerDetails);
        return ResponseEntity.ok(updatedCustomer);
    }

    // DELETE - Delete a customer
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Customer with id " + id + " has been deleted successfully");
    }
}
