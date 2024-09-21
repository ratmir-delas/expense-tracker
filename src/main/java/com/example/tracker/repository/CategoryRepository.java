package com.example.tracker.repository;

import com.example.tracker.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findCategoryByName(String name);

    Collection<Category> findByBudgetId(Long budgetId);

    boolean existsByIdAndBudgetId(Long id, Long budgetId);

    Optional<Category> findByIdAndBudgetId(Long id, Long budgetId);

}
