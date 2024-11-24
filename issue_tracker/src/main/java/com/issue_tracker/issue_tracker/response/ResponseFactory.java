package com.issue_tracker.issue_tracker.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseFactory {
    
    public ResponseEntity<Object> success(HttpBodyResponse data) {
        return ResponseEntity
        .status(data.getStatusCode())
        .body(data);
    }

    public ResponseEntity<Object> badRequest(String message) {

        HttpBodyResponse data = new HttpBodyResponse
                                .Builder()
                                .status("Error")
                                .statusCode(400)
                                .message("peticion no valida")
                                .userFriendlyMessage(message)
                                .build();
        return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(data);
    }

    public ResponseEntity<Object> errorNotFound(String message) {

        HttpBodyResponse data = new HttpBodyResponse
                                .Builder()
                                .status("Error")
                                .statusCode(404)
                                .message("recurso no encontrado")
                                .userFriendlyMessage(message)
                                .build();
        return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(data);
    }

    public ResponseEntity<Object> unauthorizedError() {

        HttpBodyResponse data = new HttpBodyResponse
                                .Builder()
                                .status("Error")
                                .statusCode(401)
                                .message("credenciales no validas")
                                .userFriendlyMessage("Por favor, verifica tus credenciales y vuelve a intentarlo")
                                .build();

        return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(data);
    }

    public ResponseEntity<Object> errorForbidden() {

        HttpBodyResponse data = new HttpBodyResponse
                                .Builder()
                                .status("Error")
                                .statusCode(403)
                                .message("acceso denegado")
                                .userFriendlyMessage("No tienes permiso para acceder a este recurso")
                                .build();

        return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body(data);
    }

    public ResponseEntity<Object> internalServerError() {

        HttpBodyResponse data = new HttpBodyResponse
                                .Builder()
                                .status("Error")
                                .statusCode(500)
                                .message("error intero del servidor")
                                .userFriendlyMessage("El servicio no se encuentra disponible. Por favor, inténtalo más tarde")
                                .build();

        return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(data);
    }

}