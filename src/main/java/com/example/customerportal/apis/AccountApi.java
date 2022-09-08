package com.example.customerportal.apis;

import com.example.customerportal.services.AccountService;
import com.example.customerportal.viewmodels.AccountCreateViewModel;
import com.example.customerportal.viewmodels.AccountUpdateViewModel;
import com.example.customerportal.viewmodels.AccountViewModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/accounts")
@CrossOrigin(origins = {"http://localhost:4200"})
public class AccountApi {
    private final AccountService accountService;

    public AccountApi(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<AccountViewModel>> get() {
        return ResponseEntity.ok(accountService.get());
    }

    @GetMapping("{id}")
    public ResponseEntity<AccountViewModel> get(@PathVariable long id) {
        return ResponseEntity.ok(accountService.get(id));
    }

    @GetMapping("by-customer/{customerId}")
    public ResponseEntity<List<AccountViewModel>> getByCustomer(@PathVariable long customerId) {
        return ResponseEntity.ok(accountService.getByCustomerId(customerId));
    }

    @PostMapping
    public ResponseEntity<AccountViewModel> create(@RequestBody AccountCreateViewModel viewModel) {
        return ResponseEntity.ok(accountService.create(viewModel));
    }

    @PutMapping("{id}")
    public ResponseEntity<AccountViewModel> update(@RequestBody AccountUpdateViewModel viewModel, @PathVariable long id) {
        return ResponseEntity.ok(accountService.update(id, viewModel));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
