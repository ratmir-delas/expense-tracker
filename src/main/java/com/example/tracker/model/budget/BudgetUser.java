package com.example.tracker.model.budget;

import com.example.tracker.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetUser {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Budget budget;

    @ManyToOne
    private User user;

}
