package com.issue_tracker.issue_tracker.config;

import java.io.IOException;
import java.util.Map;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.issue_tracker.issue_tracker.jwt.JwtToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain chain)
    throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            
            String authToken = authHeader.substring(7);

            if (authToken == null) { 
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No se ha encontrado token de autenticación");
            }

            Map<String, Object> payload = JwtToken.getPayload(authToken);
            Integer loggedInUserId = (Integer) payload.get("id");
            String userEmail = (String) payload.get("email");
            String userName = (String) payload.get("nombreUsuario");
            String role = (String) payload.get("tipo");

            if (loggedInUserId == null) { 
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token no valido");
            }

            CustomUserDetails userDetails = new CustomUserDetails(
                userName,
                userEmail,
                role,
                loggedInUserId
            );

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
            );

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder
            .getContext()
            .setAuthentication(authentication);
                
        }

        chain.doFilter(request, response);
    }
}