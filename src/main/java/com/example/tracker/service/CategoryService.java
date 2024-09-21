package com.example.tracker.service;

import com.example.tracker.model.Category;

import java.util.Collection;
import java.util.Optional;

public interface CategoryService {

    Collection<Category> getAllCategories(Long budgetId);

    Optional<Category> getCategoryById(Long budgetId, Long categoryId);

    Category createCategory(Long budgetId, Category category);

    Optional<Category> updateCategory(Long budgetId, Long categoryId, Category category);

    boolean deleteCategory(Long budgetId, Long categoryId);

}
