package com.issue_tracker.issue_tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issue_tracker.issue_tracker.dto.Login.Credentials;
import com.issue_tracker.issue_tracker.dto.Login.CurrentUser;
import com.issue_tracker.issue_tracker.dto.LoginRequest;
import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.response.HttpBodyResponse;
import com.issue_tracker.issue_tracker.response.ResponseFactory;
import com.issue_tracker.issue_tracker.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @GetMapping("/checkHealth")
    public ResponseEntity<HttpBodyResponse> checkHealth() {

        String message = "API says Hello Isabella!";

        HttpBodyResponse response = new HttpBodyResponse
        .Builder()
        .status("Success")
        .statusCode(200)
        .message(message)
        .build();

        return ResponseEntity
        .status(200)
        .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<HttpBodyResponse> login(
        @RequestBody LoginRequest emailAndPassword
    ) {
        
        try {
            
            String email = emailAndPassword.getEmail();
            String password = emailAndPassword.getPassword();
            
            Usuario user = authService.login(email, password);

            CurrentUser currentUser = new CurrentUser(
                user.getId(),
                user.getNombreUsuario(), 
                user.getEmail(), 
                user.getTipo()
            );

            String authToken = authService.generateToken(user);

            Credentials credentials = new Credentials(authToken, currentUser);
            
            HttpBodyResponse response = new HttpBodyResponse
            .Builder()
            .status("Success")
            .statusCode(200)
            .message("Usuario autenticado con exito")
            .data(credentials)
            .build();
    
            return ResponseEntity
            .status(200)
            .body(response);

        }   catch (BadRequestException e) {
                return ResponseFactory.badRequest(e.getMessage());
        }   catch (Exception e) {
                return ResponseFactory.internalServerError();
        }
    }
}