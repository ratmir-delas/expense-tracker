package com.example.tracker.model.transaction;

import com.example.tracker.model.budget.Budget;
import com.example.tracker.model.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Transaction {

    @Id
    @GeneratedValue
    protected Long id;

    protected double amount;

    protected String currency;

    protected Instant date;

    protected String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    abstract String getType();

}
