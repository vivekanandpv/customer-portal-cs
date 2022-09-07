package com.example.customerportal.services;

import com.example.customerportal.entities.Account;
import com.example.customerportal.entities.Customer;
import com.example.customerportal.exceptions.DomainInvariantException;
import com.example.customerportal.exceptions.RecordNotFoundException;
import com.example.customerportal.repositories.AccountRepository;
import com.example.customerportal.repositories.CustomerRepository;
import com.example.customerportal.utils.DomainMapper;
import com.example.customerportal.viewmodels.AccountCreateViewModel;
import com.example.customerportal.viewmodels.AccountUpdateViewModel;
import com.example.customerportal.viewmodels.AccountViewModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplementation implements AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final DomainMapper mapper;

    public AccountServiceImplementation
            (
                    AccountRepository accountRepository,
                    CustomerRepository customerRepository,
                    DomainMapper mapper
            ) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    @Override
    public List<AccountViewModel> get() {
        return
                accountRepository
                        .findAll()
                        .stream()
                        .map(a -> mapper.toViewModel(a, true))
                        .collect(Collectors.toList());
    }

    @Override
    public AccountViewModel get(long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Could not find the account with id: %d", id)));

        return mapper.toViewModel(account, true);
    }

    @Override
    public List<AccountViewModel> getByCustomerId(long customerId) {
        return
                accountRepository
                        .findAll()
                        .stream()
                        .filter(a -> a.getCustomerId() == customerId)
                        .map(a -> mapper.toViewModel(a, true))
                        .collect(Collectors.toList());
    }

    @Override
    public AccountViewModel create(AccountCreateViewModel viewModel) {
        Customer customer = customerRepository.findById(viewModel.getCustomerId())
                .orElseThrow(
                        () ->
                                new RecordNotFoundException(
                                        String.format("Customer with id %d is not found", viewModel.getCustomerId())
                                )
                );

        Account account = new Account();
        BeanUtils.copyProperties(viewModel, account);
        account.setActive(true);
        account.setOpenedOn(LocalDate.now());
        account.setCustomer(customer);
        customer.getAccounts().add(account);

        accountRepository.saveAndFlush(account);
        customerRepository.saveAndFlush(customer);

        return mapper.toViewModel(account, true);
    }

    @Override
    public AccountViewModel update(long id, AccountUpdateViewModel viewModel) {
        if (id != viewModel.getId()) {
            throw new DomainInvariantException("Account id discrepancy found");
        }

        Account account = accountRepository.findById(id)
                .orElseThrow(
                        () ->
                                new RecordNotFoundException(
                                        String.format("Account with id %d is not found", id)
                                )
                );

        BeanUtils.copyProperties(viewModel, account);

        accountRepository.saveAndFlush(account);

        return mapper.toViewModel(account, true);
    }

    @Override
    public void delete(long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(
                        () ->
                                new RecordNotFoundException(
                                        String.format("Account with id %d is not found", id)
                                )
                );

        accountRepository.delete(account);
    }
}
