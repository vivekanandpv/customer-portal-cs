package com.example.customerportal.services;

import com.example.customerportal.viewmodels.CustomerCreateViewModel;
import com.example.customerportal.viewmodels.CustomerUpdateViewModel;
import com.example.customerportal.viewmodels.CustomerViewModel;

import java.util.List;

public interface CustomerService {
    List<CustomerViewModel> get();
    CustomerViewModel get(long id);
    CustomerViewModel create(CustomerCreateViewModel viewModel);
    CustomerViewModel update(long id, CustomerUpdateViewModel viewModel);
    void delete(long id);
}
