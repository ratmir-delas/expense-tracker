package com.example.tracker.model.transaction;

import com.example.tracker.model.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    private Category category;

    @Override
    public String getType() {
        return "Expense";
    }

}
