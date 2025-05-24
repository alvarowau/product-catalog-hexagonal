package org.alvarowau.productcatalog.domain.model;

import lombok.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Representa un producto en el catálogo de la aplicación.
 * <p>
 * Contiene información básica como nombre, descripción, precio, stock,
 * categoría y estado del producto. Incluye validaciones automáticas
 * para asegurar la integridad de los datos.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Product {

    /**
     * Identificador único del producto.
     */
    @Setter
    private Long id;

    /**
     * Nombre del producto.
     */
    @Setter
    private String name;

    /**
     * Descripción detallada del producto.
     */
    private String description;

    /**
     * Precio del producto. No puede ser negativo.
     */
    private BigDecimal price;

    /**
     * Cantidad disponible en inventario. No puede ser negativo.
     */
    private Integer stock;

    /**
     * Categoría a la que pertenece el producto.
     */
    private Category category;

    /**
     * Estado actual del producto (disponible, agotado, etc.).
     */
    private Status status;

    /**
     * Constructor para crear un nuevo producto sin ID.
     *
     * @param name Nombre del producto
     * @param description Descripción del producto
     * @param price Precio del producto
     * @param stock Cantidad en inventario
     * @param category Categoría del producto
     * @param status Estado inicial del producto
     */
    public Product(String name, String description, BigDecimal price, Integer stock, Category category, Status status) {
        this.name = name;
        setDescription(description);
        setPrice(price);
        setStock(stock);
        setCategory(category);
        setStatus(status);
    }

    /**
     * Establece el precio del producto.
     * <p>
     * Si el precio es nulo o negativo, se establece a cero automáticamente.
     *
     * @param price Nuevo precio del producto
     */
    public void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            this.price = BigDecimal.ZERO;
        } else {
            this.price = price;
        }
    }

    /**
     * Establece la cantidad en stock del producto.
     * <p>
     * Si el stock es nulo o negativo, se establece a cero automáticamente.
     *
     * @param stock Nueva cantidad en inventario
     */
    public void setStock(Integer stock) {
        if (stock == null || stock < 0) {
            this.stock = 0;
        } else {
            this.stock = stock;
        }
    }

    /**
     * Establece la descripción del producto.
     * <p>
     * Si la descripción es nula o vacía, se establece como "No disponible".
     *
     * @param description Nueva descripción del producto
     */
    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            this.description = "No disponible";
        } else {
            this.description = description;
        }
    }

    /**
     * Establece la categoría del producto.
     * <p>
     * Si la categoría es nula, se establece como DEFAULT automáticamente.
     *
     * @param category Nueva categoría del producto
     */
    public void setCategory(Category category) {
        this.category = Objects.requireNonNullElse(category, Category.DEFAULT);
    }

    /**
     * Establece el estado del producto.
     * <p>
     * Si el estado es nulo, se establece como DEFAULT.
     * Si el stock es cero y el producto no está descontinuado,
     * el estado se cambia automáticamente a OUT_OF_STOCK.
     *
     * @param status Nuevo estado del producto
     */
    public void setStatus(Status status) {
        if (status == null) status = Status.DEFAULT;
        if (stock == 0 && status != Status.DISCONTINUED) {
            this.status = Status.OUT_OF_STOCK;
        } else {
            this.status = status;
        }
    }
}