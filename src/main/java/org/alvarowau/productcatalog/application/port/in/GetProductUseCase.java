package org.alvarowau.productcatalog.application.port.in;

import org.alvarowau.productcatalog.infrastructure.adapter.in.dto.ProductResponse;

import java.util.List;
import java.util.Optional;

/**
 * Caso de uso para la consulta de productos.
 * <p>
 * Define el contrato para operaciones de lectura de productos siguiendo el principio
 * de Arquitectura Hexagonal como puerto de entrada.
 */
public interface GetProductUseCase {

    /**
     * Obtiene un producto específico por su identificador único.
     * <p>
     * Implementaciones deben:
     * <ul>
     *   <li>Buscar el producto en el repositorio de datos</li>
     *   <li>Convertir la entidad de dominio a DTO de respuesta</li>
     *   <li>Manejar adecuadamente casos donde el producto no exista</li>
     * </ul>
     *
     * @param id Identificador único del producto a buscar
     * @return Optional conteniendo el ProductResponse si se encuentra el producto,
     *         o Optional vacío si no existe
     * @throws IllegalArgumentException si el ID proporcionado es nulo
     */
    Optional<ProductResponse> getProductById(Long id);

    /**
     * Obtiene todos los productos disponibles en el catálogo.
     * <p>
     * Implementaciones deben:
     * <ul>
     *   <li>Recuperar todos los productos del repositorio</li>
     *   <li>Convertir cada entidad de dominio a su DTO de respuesta</li>
     *   <li>Manejar adecuadamente casos donde no existan productos</li>
     * </ul>
     *
     * @return Lista de ProductResponse con todos los productos disponibles.
     *         Retorna una lista vacía si no hay productos.
     */
    List<ProductResponse> getAllProducts();
}