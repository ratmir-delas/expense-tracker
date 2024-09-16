package com.example.tracker.repository;

import com.example.tracker.model.transaction.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findExpensesByBudgetId(int budget_id);
}
