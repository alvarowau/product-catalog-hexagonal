package org.alvarowau.productcatalog.application.port.in;

import org.alvarowau.productcatalog.infrastructure.adapter.in.dto.CreateProductRequest;
import org.alvarowau.productcatalog.infrastructure.adapter.in.dto.ProductResponse;

/**
 * Caso de uso para la creación de nuevos productos.
 * <p>
 * Define el contrato para la operación de creación de productos en el sistema,
 * siguiendo el principio de Arquitectura Hexagonal al ser un puerto de entrada.
 */
public interface CreateProductUseCase {

    /**
     * Crea un nuevo producto a partir de los datos proporcionados.
     * <p>
     * Implementaciones de este método deben:
     * <ul>
     *   <li>Validar los datos de entrada</li>
     *   <li>Crear una nueva entidad Product</li>
     *   <li>Persistir el producto en el repositorio</li>
     *   <li>Retornar la representación DTO del producto creado</li>
     * </ul>
     *
     * @param request DTO con los datos necesarios para crear el producto
     * @return ProductResponse con los datos del producto creado
     * @throws IllegalArgumentException si los datos de entrada no son válidos
     */
    ProductResponse createProduct(CreateProductRequest request);
}