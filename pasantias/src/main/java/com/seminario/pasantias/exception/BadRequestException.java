package com.seminario.pasantias.exception;

/**
 * Excepción lanzada cuando una solicitud viola reglas de negocio o contiene datos inválidos.
 * Mapeada a HTTP 400 (BAD REQUEST).
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
