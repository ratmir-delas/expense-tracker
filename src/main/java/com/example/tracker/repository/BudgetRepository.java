package com.example.tracker.repository;

import com.example.tracker.model.budget.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    @Query("SELECT b FROM Budget b JOIN b.users u WHERE u.id = :userId")
    Set<Budget> findBudgetsByUsersId(Long userId);

    @Query("SELECT COUNT(b) FROM Budget b JOIN b.users u WHERE u.id = :userId")
    int countBudgetsByUsersId(Long userId);

}
