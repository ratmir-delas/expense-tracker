package com.example.tracker.service;

import com.example.tracker.model.budget.Budget;

import java.util.Collection;
import java.util.Optional;

public interface BudgetService {

    Budget createBudget(Budget budget);

    Optional<Budget> getBudgetById(Long id);

    Collection<Budget> getAllBudgets(); // only for development

    Collection<Budget> getBudgetsByUserId(Long userId);

    Optional<Budget> updateBudget(Long id, Budget budget);

    boolean deleteBudget(Long id);

}
