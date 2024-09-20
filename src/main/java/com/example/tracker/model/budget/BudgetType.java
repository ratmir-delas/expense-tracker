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

    private boolean allowsIncome;

    private boolean allowsSharing;

}
