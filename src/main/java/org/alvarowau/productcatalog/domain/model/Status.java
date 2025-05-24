package org.alvarowau.productcatalog.domain.model;

import lombok.Getter;

/**
 * Enumeración que representa los estados posibles de un producto.
 * Cada estado incluye un nombre legible para mostrar en la interfaz de usuario.
 */
@Getter
public enum Status {
    /** Estado por defecto cuando no se especifica uno. */
    DEFAULT("Desconocido"),
    /** El producto está disponible para compra. */
    AVAILABLE("Disponible"),
    /** El producto está agotado (sin stock). */
    OUT_OF_STOCK("Agotado"),
    /** El producto ha sido descontinuado y ya no se fabrica. */
    DISCONTINUED("Descontinuado"),
    /** El producto estará disponible próximamente. */
    COMING_SOON("Próximamente");

    private final String displayName;

    /**
     * Constructor del estado.
     *
     * @param displayName Nombre legible para mostrar en la interfaz.
     */
    Status(String displayName) {
        this.displayName = displayName;
    }
}