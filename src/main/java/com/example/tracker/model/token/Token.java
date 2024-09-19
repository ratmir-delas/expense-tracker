package com.example.tracker.model.token;

import com.example.tracker.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {

    @Id
    @GeneratedValue
    private Long id;

    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType type;

    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
