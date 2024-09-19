package com.example.tracker.config;

import com.example.tracker.model.token.TokenType;
import com.example.tracker.repository.TokenRepository;
import com.example.tracker.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
// A filter that validates JWT tokens in the Authorization header of incoming requests.
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Extract JWT token from Authorization header
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // Continue with filter chain if Authorization header is not valid
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract JWT and username from Authorization header
        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt);

        // Authenticate user if JWT is valid and user is not already authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            var tokenOpt = tokenRepository.findByToken(jwt);
            var isTokenValid = tokenRepository.findByToken(jwt)
                    .map(token -> !token.isRevoked())
                    .orElse(false);

            if (tokenOpt.isPresent() && tokenOpt.get().getType() == TokenType.ACCESS && jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                // Create and set authentication token in SecurityContext
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);

            } else if (tokenOpt.isPresent() && tokenOpt.get().getType() == TokenType.REFRESH) {
                // Reject if a refresh token is being used in this context
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Refresh tokens cannot be used for accessing resources.");
                return;
            } else {
                // Reject if the token is invalid
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid token.");
                return;
            }
        }

        filterChain.doFilter(request, response);

    }
}
