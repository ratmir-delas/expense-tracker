package com.example.tracker.model.budget;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetType {

    @Id
    private Long id;

    private String name;  // e.g., "Personal", "Family", "Business", "Party Preparation"
    private String description;
    private boolean allowsIncome;  // Determines if the template tracks income
    private boolean allowsSharing; // Determines if the budget can be shared with others

    @ElementCollection
    private List<String> defaultCategories;  // Default spending categories for this template

    // Additional fields for default limits, etc.

}
