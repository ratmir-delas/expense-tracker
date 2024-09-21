package com.example.tracker.repository;

import com.example.tracker.model.budget.BudgetAccess;
import com.example.tracker.model.budget.BudgetAccessLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetAccessRepository extends JpaRepository<BudgetAccess, Long> {

    BudgetAccess findByBudgetIdAndUserId(Long budgetId, Long userId);

    void deleteByBudgetId(Long budgetId);

}
