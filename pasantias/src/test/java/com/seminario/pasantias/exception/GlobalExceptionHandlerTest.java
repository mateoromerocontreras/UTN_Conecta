package com.seminario.pasantias.exception;

import com.seminario.pasantias.response.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleForbidden_shouldReturn403WithNegativeCode() {
        SecurityException ex = new SecurityException("No tienes permiso para realizar esta acción");

        ResponseEntity<ApiResponse<Void>> response = exceptionHandler.handleForbidden(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().codigo()).isEqualTo(-1);
        assertThat(response.getBody().mensaje()).isEqualTo("No tienes permiso para realizar esta acción");
        assertThat(response.getBody().data()).isNull();
    }

    @Test
    void handleForbidden_withAccessDeniedException_shouldReturn403() {
        AccessDeniedException ex = new AccessDeniedException("Acceso denegado");

        ResponseEntity<ApiResponse<Void>> response = exceptionHandler.handleForbidden(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().codigo()).isEqualTo(-1);
        assertThat(response.getBody().mensaje()).isEqualTo("Acceso denegado");
    }

    @Test
    void handleBadRequest_shouldReturn400WithNegativeCode() {
        IllegalArgumentException ex = new IllegalArgumentException("La carrera con ID 99 no existe");

        ResponseEntity<ApiResponse<Void>> response = exceptionHandler.handleBadRequest(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().codigo()).isEqualTo(-1);
        assertThat(response.getBody().mensaje()).isEqualTo("La carrera con ID 99 no existe");
        assertThat(response.getBody().data()).isNull();
    }

    @Test
    void handleNotFound_shouldReturn404WithNegativeCode() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Pasantía", 42);

        ResponseEntity<ApiResponse<Void>> response = exceptionHandler.handleNotFound(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().codigo()).isEqualTo(-1);
        assertThat(response.getBody().mensaje()).contains("Pasantía no encontrado con identificador: 42");
        assertThat(response.getBody().data()).isNull();
    }

    @Test
    void handleGeneralException_shouldReturn500() {
        RuntimeException ex = new RuntimeException("Fallo imprevisto");

        ResponseEntity<ApiResponse<Void>> response = exceptionHandler.handleGeneralException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().codigo()).isEqualTo(-1);
        assertThat(response.getBody().mensaje()).contains("Error interno del servidor: Fallo imprevisto");
        assertThat(response.getBody().data()).isNull();
    }
}
