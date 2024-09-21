package com.example.tracker.service;

import com.example.tracker.model.budget.Budget;
import com.example.tracker.model.budget.BudgetAccess;
import com.example.tracker.model.budget.BudgetAccessLevel;
import com.example.tracker.model.user.User;
import com.example.tracker.repository.BudgetAccessRepository;
import com.example.tracker.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final BudgetAccessRepository budgetAccessRepository;

    @Override
    public Collection<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    @Override
    public Optional<Budget> getBudgetById(Long id) {
        return budgetRepository.findById(id);
    }

    @Override
    public Collection<Budget> getBudgetsByUserId(Long userId) {
        return budgetRepository.findBudgetsByUsersId(userId);
    }

    @Override
    public int getBudgetsCountByUserId(Long userId) {
        return budgetRepository.countBudgetsByUsersId(userId);
    }

    @Override
    @Transactional
    public Budget createBudget(Budget budget, Long userId) {
        var savedBudget = budgetRepository.save(budget);
        BudgetAccess budgetAccess = BudgetAccess.builder()
                .budget(savedBudget)
                .user(User.builder().id(userId).build())
                .accessLevel(BudgetAccessLevel.OWNER)
                .build();
        budgetAccessRepository.save(budgetAccess);
        return savedBudget;
    }

    @Override
    @Transactional
    public Optional<Budget> updateBudget(Long id, Budget budget) {
        return budgetRepository.findById(id)
                .map(b -> {
                    b.setName(budget.getName());
                    b.setAmount(budget.getAmount());
                    return budgetRepository.save(b);
                });
    }

    @Override
    @Transactional
    public boolean deleteBudget(Long id) {
        if (budgetRepository.existsById(id)) {
            // Delete associated BudgetAccess entities
            budgetAccessRepository.deleteByBudgetId(id);
            // Delete the Budget entity
            budgetRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean hasAccess(Long budgetId, Long userId, BudgetAccessLevel requiredAccessLevel) {
        BudgetAccess budgetAccess = budgetAccessRepository.findByBudgetIdAndUserId(budgetId, userId);
        if (budgetAccess == null) {
            return false;
        }
        return budgetAccess.getAccessLevel().ordinal() >= requiredAccessLevel.ordinal();
    }

}
