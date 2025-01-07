package com.issue_tracker.issue_tracker.service.EnviarEmail;

import java.util.ArrayList;
import java.util.List;

import org.thymeleaf.context.Context;

public class EmailBuilder {
    
    private String asunto;
    private String nombreTemplate;
    private List<String> destinatarios = new ArrayList<>();
    private Context context = new Context();

    public EmailBuilder buildAsunto(String asunto) {
        this.asunto = asunto;
        return this;
    }

    public EmailBuilder buildTemplateHtml(String template) {
        this.nombreTemplate = template;
        return this;
    }

    public EmailBuilder buildDestinatario(String destinatario) {
        this.destinatarios.add(destinatario);
        return this;
    }

    public EmailBuilder buildContext(String key, Object value) {
        this.context.setVariable(key, value);
        return this;
    }

    public Email build() {
        return new Email(
            this.asunto,
            this.nombreTemplate,
            String.join(", ", this.destinatarios),
            this.context
        );
    }
}