package com.example.tracker.model.budget;

import com.example.tracker.model.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Budget {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private double amount;

    private String currency;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Category> categories;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<BudgetAccess> users;

}
