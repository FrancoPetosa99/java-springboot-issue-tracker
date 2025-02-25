package com.issue_tracker.issue_tracker.dto.Login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrentUser {
    private Integer id;
    private String nombreUsuario;
    private String email;
    private String role;
}