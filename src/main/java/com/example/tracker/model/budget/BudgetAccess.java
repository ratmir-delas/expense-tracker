package com.example.tracker.model.budget;

import com.example.tracker.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BudgetAccess {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Budget budget;

    @Enumerated(EnumType.STRING)
    private BudgetAccessLevel accessLevel;

}
