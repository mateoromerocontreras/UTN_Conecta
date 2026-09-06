package com.seminario.pasantias.exception;

import com.seminario.pasantias.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * Manejador global centralizado de excepciones para todos los controladores REST.
 *
 * <p>Intercepta excepciones no capturadas y las traduce a una respuesta consistente
 * {@link ApiResponse} con el código HTTP adecuado, eliminando la necesidad de
 * bloques try-catch repetitivos en los controladores.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Maneja errores de autorización (Spring Security y validaciones de seguridad de negocio).
     * Mapeado a HTTP 403 (FORBIDDEN).
     */
    @ExceptionHandler({SecurityException.class, AccessDeniedException.class, ForbiddenOperationException.class})
    public ResponseEntity<ApiResponse<Void>> handleForbidden(Exception ex) {
        log.warn("Acceso denegado o prohibido: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error(-1, ex.getMessage()));
    }

    /**
     * Maneja errores de argumentos inválidos y violaciones de reglas de negocio.
     * Mapeado a HTTP 400 (BAD REQUEST).
     */
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class, BadRequestException.class})
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(Exception ex) {
        log.warn("Solicitud inválida: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(-1, ex.getMessage()));
    }

    /**
     * Maneja recursos no encontrados.
     * Mapeado a HTTP 404 (NOT FOUND).
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(ResourceNotFoundException ex) {
        log.warn("Recurso no encontrado: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(-1, ex.getMessage()));
    }

    /**
     * Maneja errores de validación de Bean Validation (@Valid en request bodies).
     * Mapeado a HTTP 400 (BAD REQUEST).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String validationErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        log.warn("Error de validación de datos: {}", validationErrors);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(-1, "Error de validación: " + validationErrors));
    }

    /**
     * Manejador de respaldo para cualquier error inesperado del servidor.
     * Mapeado a HTTP 500 (INTERNAL SERVER ERROR).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
        log.error("Error inesperado en el servidor: ", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(-1, "Error interno del servidor: " + ex.getMessage()));
    }
}
