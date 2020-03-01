package com.revolut.models;

import java.time.OffsetDateTime;
import java.util.UUID;

public class Account {

    private UUID id;
    private Double balance;
    private OffsetDateTime creationDate;

    public Account(UUID id, Double balance, OffsetDateTime creationDate) {
        this.id = id;
        this.balance = balance;
        this.creationDate = creationDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
