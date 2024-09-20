package com.example.tracker.controller;

import com.example.tracker.model.budget.Budget;
import com.example.tracker.model.budget.BudgetAccessLevel;
import com.example.tracker.service.BudgetService;
import com.example.tracker.service.auth.JwtService;
import com.example.tracker.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("api/v1/budget")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;
    private final JwtService jwtService;

    @GetMapping("/user")
    public Collection<Budget> getBudgetsByToken(HttpServletRequest request) {
        String token = TokenUtil.extractTokenFromRequest(request);
        Long userId = jwtService.extractUserId(token);
        return budgetService.getBudgetsByUserId(userId);
    }

    @GetMapping("/{budgetId}")
    public ResponseEntity<Budget> getBudgetById(@PathVariable Long budgetId, HttpServletRequest request) {
        Budget budget = budgetService.getBudgetById(budgetId)
                .orElseThrow(() -> new IllegalArgumentException("Budget not found"));
        String token = TokenUtil.extractTokenFromRequest(request);
        Long userId = jwtService.extractUserId(token);
        if (!budgetService.verifyAccess(budgetId, userId, BudgetAccessLevel.VIEWER)) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(budget);
    }

    @PostMapping
    public ResponseEntity<Budget> createBudget(@Validated @RequestBody Budget budget, HttpServletRequest request) {
        String token = TokenUtil.extractTokenFromRequest(request);
        Long userId = jwtService.extractUserId(token);
        // verify that the user has not exceeded the budget limit
        if (budgetService.getBudgetsCountByUserId(userId) >= 5) {
            return ResponseEntity.status(403).build();
        }
        Budget createdBudget = budgetService.createBudget(budget, userId);
        URI location = URI.create(String.format("/api/v1/budget/%d", createdBudget.getId()));
        return ResponseEntity.created(location).body(createdBudget);
    }

    @PutMapping("/{budgetId}")
    public ResponseEntity<Budget> updateBudget(@PathVariable Long budgetId, @Validated @RequestBody Budget budget, HttpServletRequest request) {
        String token = TokenUtil.extractTokenFromRequest(request);
        Long userId = jwtService.extractUserId(token);
        if (!budgetService.verifyAccess(budgetId, userId, BudgetAccessLevel.EDITOR)) {
            return ResponseEntity.status(403).build();
        }
        Budget updatedBudget = budgetService.updateBudget(budgetId, budget)
                .orElseThrow(() -> new IllegalArgumentException("Budget not found"));
        return ResponseEntity.ok(updatedBudget);
    }

    @DeleteMapping("/{budgetId}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long budgetId, HttpServletRequest request) {
        String token = TokenUtil.extractTokenFromRequest(request);
        Long userId = jwtService.extractUserId(token);
        if (!budgetService.verifyAccess(budgetId, userId, BudgetAccessLevel.OWNER)) {
            return ResponseEntity.status(403).build();
        }
        if (budgetService.deleteBudget(budgetId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
