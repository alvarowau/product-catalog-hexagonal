package org.alvarowau.productcatalog.infrastructure.adapter.in.dto;

import lombok.*;
import org.alvarowau.productcatalog.domain.model.Category;
import org.alvarowau.productcatalog.domain.model.Status;
import java.math.BigDecimal;

/**
 * DTO para la solicitud de actualización de productos.
 * <p>
 * Contiene los campos que pueden ser modificados en un producto existente.
 * Los campos nulos serán ignorados en la actualización (actualización parcial).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {

    /**
     * Nuevo nombre para el producto.
     * <p>
     * Restricciones:
     * - No vacío si se proporciona
     * - Longitud máxima: 100 caracteres
     */
    private String name;

    /**
     * Nueva descripción para el producto.
     * <p>
     * Restricciones:
     * - No vacía si se proporciona
     * - Longitud máxima: 500 caracteres
     */
    private String description;

    /**
     * Nuevo precio para el producto.
     * <p>
     * Restricciones:
     * - Debe ser positivo si se proporciona
     * - Formato: BigDecimal para precisión monetaria
     */
    private BigDecimal price;

    /**
     * Nuevo stock disponible.
     * <p>
     * Restricciones:
     * - Entero no negativo si se proporciona
     */
    private Integer stock;

    /**
     * Nueva categoría para el producto.
     * <p>
     * Si se proporciona, debe ser una categoría válida existente.
     */
    private Category category;

    /**
     * Nuevo estado para el producto.
     * <p>
     * Si se proporciona, debe ser un estado válido.
     * Nota: Algunos estados pueden cambiar automáticamente basados en el stock.
     */
    private Status status;
}