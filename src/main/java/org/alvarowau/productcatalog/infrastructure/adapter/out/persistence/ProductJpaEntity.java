package org.alvarowau.productcatalog.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.*;
import org.alvarowau.productcatalog.domain.model.Category;
import org.alvarowau.productcatalog.domain.model.Status;
import java.math.BigDecimal;

/**
 * Entidad JPA que representa la tabla de productos en la base de datos.
 * <p>
 * Mapea los atributos de un producto a una estructura relacional,
 * siguiendo las convenciones de JPA para persistencia.
 */
@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductJpaEntity {

    /**
     * Identificador único del producto en la base de datos.
     * <p>
     * Se genera automáticamente mediante una estrategia de identidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del producto.
     * <p>
     * Restricciones:
     * - No puede ser nulo
     * - Máximo 255 caracteres (valor por defecto de JPA)
     */
    @Column(nullable = false)
    private String name;

    /**
     * Descripción detallada del producto.
     * <p>
     * Restricciones:
     * - Longitud máxima: 1000 caracteres
     * - Opcional (puede ser nulo)
     */
    @Column(length = 1000)
    private String description;

    /**
     * Precio unitario del producto.
     * <p>
     * Restricciones:
     * - No puede ser nulo
     * - Precisión: 10 dígitos totales, 2 decimales
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /**
     * Cantidad disponible en inventario.
     * <p>
     * Restricciones:
     * - No puede ser nulo
     * - Valor entero
     */
    @Column(nullable = false)
    private Integer stock;

    /**
     * Categoría del producto.
     * <p>
     * Restricciones:
     * - No puede ser nula
     * - Se almacena como cadena (nombre del enum)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    /**
     * Estado actual del producto.
     * <p>
     * Restricciones:
     * - No puede ser nulo
     * - Se almacena como cadena (nombre del enum)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
}