package com.example.tracker.model.budget;

import com.example.tracker.model.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Budget {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private double amount;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Category> categories;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<BudgetUser> users;

}
