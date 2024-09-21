package com.example.tracker.service;

import com.example.tracker.model.Category;
import com.example.tracker.model.budget.Budget;
import com.example.tracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Collection<Category> getAllCategories(Long budgetId) {
        return categoryRepository.findByBudgetId(budgetId);
    }

    @Override
    public Optional<Category> getCategoryById(Long budgetId, Long categoryId) {
        return categoryRepository.findByIdAndBudgetId(categoryId, budgetId);
    }

    @Override
    @Transactional
    public Category createCategory(Long budgetId, Category category) {
        category.setBudget(
                Budget.builder().id(budgetId).build()
        );
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public Optional<Category> updateCategory(Long budgetId, Long categoryId, Category category) {
        return categoryRepository.findByIdAndBudgetId(categoryId, budgetId)
                .map(c -> {
                    c.setName(category.getName());
                    return categoryRepository.save(c);
                });
    }

    @Override
    @Transactional
    public boolean deleteCategory(Long budgetId, Long categoryId) {
        if (categoryRepository.existsByIdAndBudgetId(categoryId, budgetId)) {
            categoryRepository.deleteById(categoryId);
            return true;
        }
        return false;
    }

}
