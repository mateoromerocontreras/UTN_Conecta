package com.seminario.pasantias.exception;

/**
 * Excepción lanzada cuando un usuario autenticado intenta realizar una operación
 * para la cual no tiene permisos o acceso (ej. impersonación de empresa).
 * Mapeada a HTTP 403 (FORBIDDEN).
 */
public class ForbiddenOperationException extends RuntimeException {

    public ForbiddenOperationException(String message) {
        super(message);
    }
}
