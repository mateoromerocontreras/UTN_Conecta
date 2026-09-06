package com.seminario.pasantias.response;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Envoltorio estándar para todas las respuestas de la API REST.
 *
 * Mantiene compatibilidad total con el frontend y los tests existentes:
 * <ul>
 *   <li><b>codigo</b>: 0 para éxito, -1 (u otro código) para error</li>
 *   <li><b>mensaje</b>: mensaje descriptivo del resultado o del error</li>
 *   <li><b>data</b>: carga útil tipada de forma genérica (o null en errores)</li>
 * </ul>
 *
 * @param <T> tipo de dato contenido en la respuesta
 */
@JsonInclude(JsonInclude.Include.ALWAYS)
public record ApiResponse<T>(
        int codigo,
        String mensaje,
        T data
) {

    /**
     * Crea una respuesta exitosa con datos y mensaje personalizado.
     */
    public static <T> ApiResponse<T> success(T data, String mensaje) {
        return new ApiResponse<>(0, mensaje, data);
    }

    /**
     * Crea una respuesta exitosa con datos y mensaje por defecto.
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(0, "Operación exitosa", data);
    }

    /**
     * Crea una respuesta de error con código y mensaje especificados.
     */
    public static <T> ApiResponse<T> error(int codigo, String mensaje) {
        return new ApiResponse<>(codigo, mensaje, null);
    }

    /**
     * Crea una respuesta de error con código -1 estándar.
     */
    public static <T> ApiResponse<T> error(String mensaje) {
        return new ApiResponse<>(-1, mensaje, null);
    }
}
