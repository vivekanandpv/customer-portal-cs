package com.example.customerportal.services;

import com.example.customerportal.viewmodels.AccountCreateViewModel;
import com.example.customerportal.viewmodels.AccountUpdateViewModel;
import com.example.customerportal.viewmodels.AccountViewModel;

import java.util.List;

public interface AccountService {
    List<AccountViewModel> get();
    AccountViewModel get(long id);
    List<AccountViewModel> getByCustomerId(long customerId);
    AccountViewModel create(AccountCreateViewModel viewModel);
    AccountViewModel update(long id, AccountUpdateViewModel viewModel);
    void delete(long id);
}
