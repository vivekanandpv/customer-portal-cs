package com.example.customerportal.utils;

import com.example.customerportal.entities.Account;
import com.example.customerportal.entities.Customer;
import com.example.customerportal.viewmodels.AccountViewModel;
import com.example.customerportal.viewmodels.CustomerViewModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DomainMapper {
    public CustomerViewModel toViewModel(Customer customer, boolean withAccounts) {
        CustomerViewModel viewModel = new CustomerViewModel();
        BeanUtils.copyProperties(customer, viewModel);

        if (withAccounts) {
            List<AccountViewModel> accounts = customer
                    .getAccounts()
                    .stream()
                    .map(a -> toViewModel(a, false))
                    .collect(Collectors.toList());

            viewModel.setAccounts(accounts);
        }

        return viewModel;
    }

    public AccountViewModel toViewModel(Account account, boolean withCustomer) {
        AccountViewModel viewModel = new AccountViewModel();
        BeanUtils.copyProperties(account, viewModel);

        if (withCustomer) {
            CustomerViewModel customer = toViewModel(account.getCustomer(), false);
            viewModel.setCustomer(customer);
        }
        return viewModel;
    }
}
