package com.example.tracker.controller;

import com.example.tracker.model.budget.Budget;
import com.example.tracker.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

@RestController
@RequestMapping("api/v1/budget")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping("/")
    ResponseEntity<Budget> createBudget(@Validated @RequestBody Budget budget) throws URISyntaxException {
        Budget result = budgetService.createBudget(budget);
        return ResponseEntity.created(new URI("api/v1/budget/" + result.getId())).body(result);
    }

    @GetMapping("/all")
    Collection<Budget> getAllBudgets() {
        return budgetService.getAllBudgets();
    }

    @GetMapping("/user/{userId}")
    Collection<Budget> getBudgetsByUserId(@PathVariable Long userId) {
        return budgetService.getBudgetsByUserId(userId);
    }

    @GetMapping("/{budgetId}")
    ResponseEntity<?> getBudgetById(@PathVariable Long budgetId) {
        return budgetService.getBudgetById(budgetId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
