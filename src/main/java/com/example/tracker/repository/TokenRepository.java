package com.example.tracker.repository;

import com.example.tracker.model.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findAllByUserId(Long userId);

    @Query("SELECT t FROM Token t WHERE t.user.id = :userId AND t.revoked = false")
    List<Token> findAllPermittedTokensByUserId(Long userId);

    @Query("SELECT t FROM Token t WHERE t.user.id = :userId AND t.type = 'ACCESS' AND t.revoked = false")
    List<Token> findAllPermittedAccessTokensByUserId(Long userId);

    Optional<Token> findByToken(String token);

    void deleteByToken(String token);

    void deleteByUserId(Long userId);

}
