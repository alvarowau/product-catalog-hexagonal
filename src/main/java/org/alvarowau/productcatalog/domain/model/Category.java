package org.alvarowau.productcatalog.domain.model;

import lombok.Getter;

/**
 * Enumeración que representa las categorías de productos disponibles.
 * Cada categoría tiene un nombre legible para mostrar en la interfaz de usuario.
 */
@Getter
public enum Category {
    /** Categoría por defecto, para productos sin clasificación específica. */
    DEFAULT("General"),
    /** Categoría para productos electrónicos. */
    ELECTRONICS("Electrónica"),
    /** Categoría para artículos de moda. */
    FASHION("Moda"),
    /** Categoría para productos del hogar. */
    HOME("Hogar"),
    /** Categoría para libros. */
    BOOKS("Libros"),
    /** Categoría para artículos deportivos. */
    SPORTS("Deportes"),
    /** Categoría para juguetes. */
    TOYS("Juguetes"),
    /** Categoría para comestibles. */
    GROCERIES("Comestibles"),
    /** Categoría para productos de belleza. */
    BEAUTY("Belleza"),
    /** Categoría para productos automotrices. */
    AUTOMOTIVE("Automotriz"),
    /** Categoría para productos de salud. */
    HEALTH("Salud");

    /**
     *  Obtiene el nombre legible de la categoría.
     *
     */
    private final String displayName;

    /**
     * Constructor de la categoría.
     *
     * @param displayName El nombre legible para mostrar en la interfaz de usuario.
     */
    Category(String displayName) {
        this.displayName = displayName;
    }

}