package org.alvarowau.productcatalog.infrastructure.adapter.in.dto;

import lombok.*;
import org.alvarowau.productcatalog.domain.model.Category;
import org.alvarowau.productcatalog.domain.model.Status;

import java.math.BigDecimal;

/**
 * DTO para la solicitud de creación de un nuevo producto.
 * <p>
 * Contiene todos los datos necesarios para registrar un nuevo producto en el sistema.
 * Se utiliza como objeto de transferencia en las operaciones de creación.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {

    /**
     * Nombre del producto.
     * <p>
     * Debe ser único y descriptivo.
     */
    private String name;

    /**
     * Descripción detallada del producto.
     * <p>
     * Puede incluir características, especificaciones u otra información relevante.
     */
    private String description;

    /**
     * Precio unitario del producto.
     * <p>
     * Debe ser un valor positivo.
     */
    private BigDecimal price;

    /**
     * Cantidad inicial en inventario.
     * <p>
     * Valor entero no negativo que representa el stock disponible.
     */
    private Integer stock;

    /**
     * Categoría a la que pertenece el producto.
     * <p>
     * Determina la clasificación del producto dentro del catálogo.
     */
    private Category category;

    /**
     * Estado inicial del producto.
     * <p>
     * Indica la disponibilidad o condición inicial del producto.
     * Opcional - algunos sistemas pueden establecer este valor automáticamente.
     */
    private Status status;
}