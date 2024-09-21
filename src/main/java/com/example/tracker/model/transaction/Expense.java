package com.example.tracker.model.transaction;

import com.example.tracker.model.Category;
import com.example.tracker.model.budget.Budget;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense extends Transaction {

    @ManyToOne
    private Budget budget;

    @ManyToOne
    private Category category;

    @Override
    public String getType() {
        return "Expense";
    }

}
