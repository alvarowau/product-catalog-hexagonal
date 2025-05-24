package org.alvarowau.productcatalog.application.port.in;

import org.alvarowau.productcatalog.infrastructure.adapter.in.dto.ProductResponse;
import org.alvarowau.productcatalog.infrastructure.adapter.in.dto.UpdateProductRequest;

/**
 * Caso de uso para la actualización de productos existentes.
 * <p>
 * Define el contrato para operaciones de actualización de productos siguiendo el principio
 * de Arquitectura Hexagonal como puerto de entrada.
 */
public interface UpdateProductUseCase {

    /**
     * Actualiza un producto existente con los nuevos datos proporcionados.
     * <p>
     * Implementaciones deben:
     * <ul>
     *   <li>Buscar el producto existente por ID</li>
     *   <li>Validar los datos de actualización</li>
     *   <li>Aplicar las actualizaciones solo a los campos no nulos del request</li>
     *   <li>Persistir los cambios en el repositorio</li>
     *   <li>Retornar la representación actualizada del producto</li>
     * </ul>
     *
     * @param id Identificador único del producto a actualizar
     * @param request DTO con los nuevos datos del producto (campos nulos se ignoran)
     * @return ProductResponse con los datos actualizados del producto
     * @throws IllegalArgumentException si el ID es nulo o el request es inválido
     */
    ProductResponse updateProduct(Long id, UpdateProductRequest request);
}