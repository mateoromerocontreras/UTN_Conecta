package com.seminario.pasantias.exception;

/**
 * Excepción lanzada cuando un recurso solicitado no existe en el sistema.
 * Mapeada a HTTP 404 (NOT FOUND).
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, Object identifier) {
        super(String.format("%s no encontrado con identificador: %s", resourceName, identifier));
    }
}
