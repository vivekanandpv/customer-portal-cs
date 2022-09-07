package com.example.customerportal.viewmodels;

import java.util.ArrayList;
import java.util.List;

public class CustomerViewModel {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean active;
    private List<AccountViewModel> accounts = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<AccountViewModel> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountViewModel> accounts) {
        this.accounts = accounts;
    }
}
