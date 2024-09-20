package com.example.tracker.service;

import com.example.tracker.model.budget.Budget;
import com.example.tracker.model.budget.BudgetAccessLevel;

import java.util.Collection;
import java.util.Optional;

public interface BudgetService {

    Collection<Budget> getAllBudgets(); // only for development

    Optional<Budget> getBudgetById(Long id);

    Collection<Budget> getBudgetsByUserId(Long userId);

    int getBudgetsCountByUserId(Long userId);

    Budget createBudget(Budget budget, Long userId);

    Optional<Budget> updateBudget(Long id, Budget budget);

    boolean deleteBudget(Long id);

    boolean verifyAccess(Long budgetId, Long userId, BudgetAccessLevel accessLevel);

}
