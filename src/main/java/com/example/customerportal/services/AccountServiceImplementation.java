package com.example.customerportal.services;

import com.example.customerportal.entities.Account;
import com.example.customerportal.entities.Customer;
import com.example.customerportal.exceptions.DomainInvariantException;
import com.example.customerportal.exceptions.RecordNotFoundException;
import com.example.customerportal.repositories.AccountRepository;
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
    private final DomainMapper mapper;

    public AccountServiceImplementation
            (
                    AccountRepository accountRepository,
                    DomainMapper mapper
            ) {
        this.accountRepository = accountRepository;
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
        Account account = new Account();
        BeanUtils.copyProperties(viewModel, account);
        account.setActive(true);
        account.setOpenedOn(LocalDate.now());

        accountRepository.saveAndFlush(account);

        return get(account.getId());
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
