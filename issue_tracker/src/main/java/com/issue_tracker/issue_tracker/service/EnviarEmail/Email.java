package com.issue_tracker.issue_tracker.service.EnviarEmail;

import org.thymeleaf.context.Context;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Email {
    private String asunto;
    private String nombreTemplate;
    private String destinatarios;
    private Context context; 
}