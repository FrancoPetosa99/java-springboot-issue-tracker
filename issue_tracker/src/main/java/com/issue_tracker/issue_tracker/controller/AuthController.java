package com.issue_tracker.issue_tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.issue_tracker.issue_tracker.dto.LoginRequest;
import com.issue_tracker.issue_tracker.exception.BadRequestException;
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
        @RequestBody LoginRequest credentials
    ) {
        
        try {
            
            String email = credentials.getEmail();
            String password = credentials.getPassword();
            
            String authToken = authService.login(email, password);
            
            HttpBodyResponse response = new HttpBodyResponse
            .Builder()
            .status("Success")
            .statusCode(200)
            .message("Usuario autenticado con exito")
            .data(authToken)
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