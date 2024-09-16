package com.example.tracker.model;

import com.example.tracker.model.budget.Budget;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    private Budget budget;

}
