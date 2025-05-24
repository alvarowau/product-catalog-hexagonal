package org.alvarowau.productcatalog.application.port.in;

/**
 * Caso de uso para la eliminación de productos existentes.
 * <p>
 * Define el contrato para operaciones de eliminación de productos siguiendo el principio
 * de Arquitectura Hexagonal como puerto de entrada.
 */
public interface DeleteProductUseCase {

    /**
     * Elimina un producto existente identificado por su ID.
     * <p>
     * Implementaciones deben:
     * <ul>
     *   <li>Verificar la existencia del producto</li>
     *   <li>Realizar cualquier validación de negocio requerida</li>
     *   <li>Eliminar el producto del repositorio</li>
     *   <li>Realizar operaciones de limpieza asociadas si es necesario</li>
     * </ul>
     *
     * @param id Identificador único del producto a eliminar
     * @return true si el producto fue eliminado exitosamente, false si no se encontró el producto
     * @throws IllegalArgumentException si el ID proporcionado es nulo
     */
    boolean deleteProduct(Long id);
}