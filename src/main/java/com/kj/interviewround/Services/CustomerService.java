package com.kj.interviewround.Services;

import com.kj.interviewround.DTO.CustomerDTO;
import com.kj.interviewround.Entity.Address;
import com.kj.interviewround.Entity.Customer;
import com.kj.interviewround.Repository.CustomerRepository;
import com.kj.interviewround.Exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Create a new customer
    public Customer createCustomer(Customer customer) {
        // Set bidirectional relationship for address
        if (customer.getAddresses() != null) {
            customer.getAddresses().setCustomer(customer);
        }
        return customerRepository.save(customer);
    }

    // Get all customers
    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    // Get customer by ID
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    // Full update (PUT) - updates all fields
    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = getCustomerById(id);

        customer.setName(customerDetails.getName());
        customer.setEmail(customerDetails.getEmail());
        customer.setAge(customerDetails.getAge());
        
        // Update address if provided
        if (customerDetails.getAddresses() != null) {
            customerDetails.getAddresses().setCustomer(customer);
            customer.setAddresses(customerDetails.getAddresses());
        }

        return customerRepository.save(customer);
    }

    // Partial update (PATCH) - updates only provided fields
    public Customer partialUpdateCustomer(Long id, Customer customerDetails) {
        Customer customer = getCustomerById(id);

        if (customerDetails.getName() != null) {
            customer.setName(customerDetails.getName());
        }
        if (customerDetails.getAddresses() != null) {
            customerDetails.getAddresses().setCustomer(customer);
            customer.setAddresses(customerDetails.getAddresses());
        }
        if (customerDetails.getEmail() != null) {
            customer.setEmail(customerDetails.getEmail());
        }
        if (customerDetails.getAge() != null) {
            customer.setAge(customerDetails.getAge());
        }

        return customerRepository.save(customer);
    }

    // Delete a customer
    public void deleteCustomer(Long id) {
        Customer customer = getCustomerById(id);
        customerRepository.delete(customer);
    }

    // Convert CustomerDTO to Customer entity with Address
    public Customer convertDTOToEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setAge(dto.getAge());

        // Create Address only if address fields are provided
        if (dto.getAddressLine1() != null || dto.getCity() != null) {
            Address address = new Address();
            address.setAddressLine1(dto.getAddressLine1());
            address.setAddressLine2(dto.getAddressLine2());
            address.setCity(dto.getCity());
            address.setState(dto.getState());
            address.setCountry(dto.getCountry());
            address.setPincode(dto.getPincode());
            address.setCustomer(customer);
            customer.setAddresses(address);
        }

        return customer;
    }

    // Create customer from DTO
    public Customer createCustomerFromDTO(CustomerDTO dto) {
        Customer customer = convertDTOToEntity(dto);
        return customerRepository.save(customer);
    }
}
