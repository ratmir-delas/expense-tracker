package com.example.tracker.service;

import com.example.tracker.model.budget.Budget;

import java.util.Collection;
import java.util.Optional;

public interface BudgetService {

    Budget createBudget(Budget budget);

    Optional<Budget> getBudget(Long id);

    Collection<Budget> getAllBudgets(); // only for development

    Collection<Budget> getBudgetsByUser(Long userId);

    Optional<Budget> updateBudget(Long id, Budget budget);

    boolean deleteBudget(Long id);

}
