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

    protected Instant date;

    protected String description;

    abstract String getType();

    @ManyToOne
    private Budget budget;

    @ManyToOne
    private User createdBy;

}
