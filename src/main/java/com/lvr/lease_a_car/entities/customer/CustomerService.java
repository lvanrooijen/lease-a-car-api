package com.lvr.lease_a_car.entities.customer;

import com.lvr.lease_a_car.entities.customer.dto.CustomerMapper;
import com.lvr.lease_a_car.entities.customer.dto.GetCustomer;
import com.lvr.lease_a_car.entities.customer.dto.PatchCustomer;
import com.lvr.lease_a_car.entities.customer.dto.PostCustomer;
import com.lvr.lease_a_car.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Handles the business logic related to Customers */
@Service
@RequiredArgsConstructor
public class CustomerService {
  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;

  /**
   * Creates a user
   *
   * <p>Creates a user and saves it in the database
   *
   * @param postCustomer {@link PostCustomer} request body containing details on the customer
   * @return {@link GetCustomer} DTO containing customer information
   */
  public GetCustomer createCustomer(PostCustomer postCustomer) {
    Customer customer = customerMapper.toCustomerEntity(postCustomer);
    customerRepository.save(customer);
    return customerMapper.toGetCustomerDto(customer);
  }

  /**
   * Updates a customer
   *
   * @param id represents the customers ID
   * @param patch {@link PatchCustomer} contains the new values for the customer
   * @return {@link GetCustomer}
   * @throws CustomerNotFoundException if a customer with this ID is not present in the database
   */
  public GetCustomer patchCustomer(Long id, PatchCustomer patch) {
    Customer customer =
        customerRepository
            .findById(id)
            .orElseThrow(() -> new CustomerNotFoundException("Customer does not exist"));

    customerMapper.updateCustomerFields(customer, patch);
    customerRepository.save(customer);
    return customerMapper.toGetCustomerDto(customer);
  }

  /**
   * Deletes a customer in the database
   *
   * @param id represents the ID of the customer
   * @throws CustomerNotFoundException when a customer with this ID is not present in the database
   */
  public void deleteCustomer(Long id) {
    customerRepository
        .findById(id)
        .orElseThrow(() -> new CustomerNotFoundException("Customer does not exist"));

    customerRepository.deleteById(id);
  }
}
