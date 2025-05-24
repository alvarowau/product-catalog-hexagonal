package org.alvarowau.productcatalog.application.port.out;

import org.alvarowau.productcatalog.domain.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * Puerto para operaciones de persistencia de productos.
 * <p>
 * Define el contrato para las operaciones de acceso a datos siguiendo el patrón
 * de Arquitectura Hexagonal. Este puerto funciona como puerto de salida para
 * operaciones del repositorio de productos.
 */
public interface ProductRepositoryPort {

    /**
     * Guarda un producto en el repositorio.
     * <p>
     * Las implementaciones deben:
     * <ul>
     *   <li>Persistir productos nuevos</li>
     *   <li>Actualizar productos existentes</li>
     *   <li>Retornar la entidad gestionada del producto</li>
     * </ul>
     *
     * @param producto la entidad Producto a guardar o actualizar
     * @return la entidad Producto guardada/actualizada
     * @throws IllegalArgumentException si el producto es null
     */
    Product save(Product producto);

    /**
     * Busca un producto por su identificador único.
     *
     * @param id el ID del producto a buscar
     * @return un Optional conteniendo el producto encontrado, o vacío si no existe
     * @throws IllegalArgumentException si el id es null
     */
    Optional<Product> findById(Long id);

    /**
     * Obtiene todos los productos disponibles.
     *
     * @return una lista con todos los productos (lista vacía si no hay productos)
     */
    List<Product> findAll();

    /**
     * Elimina un producto por su identificador único.
     *
     * @param id el ID del producto a eliminar
     * @return true si el producto existía y fue eliminado, false si no existía
     * @throws IllegalArgumentException si el id es null
     */
    boolean deleteById(Long id);
}