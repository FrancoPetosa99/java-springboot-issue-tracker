package com.issue_tracker.issue_tracker.dto.Login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Credentials {
    private String authToken;
    private CurrentUser currentUser;
}