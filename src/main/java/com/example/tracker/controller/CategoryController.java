package com.example.tracker.controller;

import com.example.tracker.model.Category;
import com.example.tracker.model.budget.BudgetAccessLevel;
import com.example.tracker.service.BudgetService;
import com.example.tracker.service.CategoryService;
import com.example.tracker.service.auth.JwtService;
import com.example.tracker.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/budget/{budgetId}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final BudgetService budgetService;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<Collection<Category>> getAllCategories(@PathVariable Long budgetId, HttpServletRequest request) {
        String token = TokenUtil.extractTokenFromRequest(request);
        Long userId = jwtService.extractUserId(token);
        if (!hasAccess(budgetId, userId, BudgetAccessLevel.VIEWER)) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(categoryService.getAllCategories(budgetId));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long budgetId, @PathVariable Long categoryId, HttpServletRequest request) {
        String token = TokenUtil.extractTokenFromRequest(request);
        Long userId = jwtService.extractUserId(token);
        if (!hasAccess(budgetId, userId, BudgetAccessLevel.VIEWER)) {
            return ResponseEntity.status(403).build();
        }
        Optional<Category> category = categoryService.getCategoryById(budgetId, categoryId);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@PathVariable Long budgetId, @RequestBody Category category, HttpServletRequest request) {
        String token = TokenUtil.extractTokenFromRequest(request);
        Long userId = jwtService.extractUserId(token);
        if (!hasAccess(budgetId, userId, BudgetAccessLevel.EDITOR)) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(categoryService.createCategory(budgetId, category));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long budgetId, @PathVariable Long categoryId, @RequestBody Category category, HttpServletRequest request) {
        String token = TokenUtil.extractTokenFromRequest(request);
        Long userId = jwtService.extractUserId(token);
        if (!hasAccess(budgetId, userId, BudgetAccessLevel.EDITOR)) {
            return ResponseEntity.status(403).build();
        }
        Optional<Category> updatedCategory = categoryService.updateCategory(budgetId, categoryId, category);
        return updatedCategory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long budgetId, @PathVariable Long categoryId, HttpServletRequest request) {
        String token = TokenUtil.extractTokenFromRequest(request);
        Long userId = jwtService.extractUserId(token);
        if (!hasAccess(budgetId, userId, BudgetAccessLevel.EDITOR)) {
            return ResponseEntity.status(403).build();
        }
        if (categoryService.deleteCategory(budgetId, categoryId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private boolean hasAccess(Long budgetId, Long userId, BudgetAccessLevel requiredAccessLevel) {
        return budgetService.hasAccess(budgetId, userId, requiredAccessLevel);
    }

}
