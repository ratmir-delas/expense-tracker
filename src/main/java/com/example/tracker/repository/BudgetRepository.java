package com.example.tracker.repository;

import com.example.tracker.model.budget.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Set<Budget> findBudgetsByUsersId(Long userId);
}
