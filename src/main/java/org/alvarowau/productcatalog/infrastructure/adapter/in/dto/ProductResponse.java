package org.alvarowau.productcatalog.infrastructure.adapter.in.dto;

import lombok.*;
import org.alvarowau.productcatalog.domain.model.Category;
import org.alvarowau.productcatalog.domain.model.Status;
import java.math.BigDecimal;

/**
 * DTO para la respuesta de productos.
 * <p>
 * Representa la estructura de datos que se devuelve al cliente cuando se consulta
 * o manipula un producto. Contiene todos los atributos visibles del producto.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    /**
     * Identificador único del producto en el sistema.
     */
    private Long id;

    /**
     * Nombre del producto.
     * <p>
     * Ejemplo: "Smartphone X200"
     */
    private String name;

    /**
     * Descripción detallada del producto.
     * <p>
     * Ejemplo: "Teléfono inteligente con pantalla AMOLED de 6.5 pulgadas"
     */
    private String description;

    /**
     * Precio actual del producto.
     * <p>
     * Formato: BigDecimal para precisión en cálculos monetarios.
     * Ejemplo: 599.99
     */
    private BigDecimal price;

    /**
     * Cantidad disponible en inventario.
     * <p>
     * Valor entero no negativo.
     */
    private Integer stock;

    /**
     * Categoría del producto.
     * <p>
     * Determina la clasificación dentro del catálogo.
     * Ejemplo: ELECTRONICS
     */
    private Category category;

    /**
     * Estado actual del producto.
     * <p>
     * Indica disponibilidad o condición.
     * Ejemplo: AVAILABLE, OUT_OF_STOCK
     */
    private Status status;
}