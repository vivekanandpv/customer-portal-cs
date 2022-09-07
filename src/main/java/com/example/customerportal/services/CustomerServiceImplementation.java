package com.example.customerportal.services;

import com.example.customerportal.entities.Customer;
import com.example.customerportal.exceptions.DomainInvariantException;
import com.example.customerportal.exceptions.RecordNotFoundException;
import com.example.customerportal.repositories.CustomerRepository;
import com.example.customerportal.utils.DomainMapper;
import com.example.customerportal.viewmodels.CustomerCreateViewModel;
import com.example.customerportal.viewmodels.CustomerUpdateViewModel;
import com.example.customerportal.viewmodels.CustomerViewModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImplementation implements CustomerService {
    private final CustomerRepository customerRepository;
    private final DomainMapper mapper;

    public CustomerServiceImplementation
            (
                    CustomerRepository customerRepository,
                    DomainMapper mapper
            ) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CustomerViewModel> get() {
        return customerRepository
                .findAll()
                .stream()
                .map(c -> mapper.toViewModel(c, true))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerViewModel get(long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(
                        () ->
                                new RecordNotFoundException(
                                        String.format("Customer with id %d is not found", id)
                                )
                );

        return mapper.toViewModel(customer, true);
    }

    @Override
    public CustomerViewModel create(CustomerCreateViewModel viewModel) {
        Customer customer = new Customer();

        BeanUtils.copyProperties(viewModel, customer);

        customer.setActive(true);

        customerRepository.saveAndFlush(customer);

        return mapper.toViewModel(customer, false);
    }

    @Override
    public CustomerViewModel update(long id, CustomerUpdateViewModel viewModel) {
        if (id != viewModel.getId()) {
            throw new DomainInvariantException("Customer Id discrepancy found");
        }

        Customer customer = customerRepository.findById(id)
                .orElseThrow(
                        () ->
                                new RecordNotFoundException(
                                        String.format("Customer with id %d is not found", id)
                                )
                );

        BeanUtils.copyProperties(viewModel, customer);

        customerRepository.saveAndFlush(customer);

        return mapper.toViewModel(customer, true);
    }

    @Override
    public void delete(long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(
                        () ->
                                new RecordNotFoundException(
                                        String.format("Customer with id %d is not found", id)
                                )
                );

        customerRepository.delete(customer);
    }


}
