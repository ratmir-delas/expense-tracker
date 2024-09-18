package com.example.tracker.repository;

import com.example.tracker.model.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findAllByUserId(Long userId);

    @Query("SELECT t FROM Token t WHERE t.user.id = :userId AND t.expired = false AND t.revoked = false")
    List<Token> findAllValidByUserId(Long userId);

    @Query("SELECT t FROM Token t WHERE t.user.id = :userId AND t.type = 'ACCESS' AND t.expired = false AND t.revoked = false")
    List<Token> findAllValidAccessTokenByUserId(Long userId);

    Optional<Token> findByToken(String token);

    void deleteByToken(String token);

    void deleteByUserId(Long userId);

}
