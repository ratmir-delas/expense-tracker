package com.example.tracker.service;

import com.example.tracker.model.budget.Budget;
import com.example.tracker.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;

    @Autowired
    public BudgetServiceImpl(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    public Budget createBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    @Override
    public Optional<Budget> getBudget(Long id) {
        return budgetRepository.findById(id);
    }

    @Override
    public Collection<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    @Override
    public Collection<Budget> getBudgetsByUser(Long userId) {
        return budgetRepository.findBudgetsByUsersId(userId);
    }

    @Override
    public Optional<Budget> updateBudget(Long id, Budget budget) {
        return budgetRepository.findById(id)
                .map(b -> {
                    b.setName(budget.getName());
                    b.setAmount(budget.getAmount());
                    return budgetRepository.save(b);
                });
    }

    @Override
    public boolean deleteBudget(Long id) {
        if (budgetRepository.existsById(id)) {
            budgetRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
