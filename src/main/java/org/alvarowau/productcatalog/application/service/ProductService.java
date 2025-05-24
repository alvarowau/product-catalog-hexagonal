package org.alvarowau.productcatalog.application.service;

import org.alvarowau.productcatalog.application.mapper.ProductApplicationMapper;
import org.alvarowau.productcatalog.application.port.in.*;
import org.alvarowau.productcatalog.application.port.out.ProductRepositoryPort;
import org.alvarowau.productcatalog.domain.model.Product;
import org.alvarowau.productcatalog.infrastructure.adapter.in.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que implementa los casos de uso para la gestión de productos.
 * <p>
 * Esta clase es el núcleo de la lógica de negocio para operaciones CRUD de productos,
 * implementando los puertos de entrada definidos en la arquitectura hexagonal.
 */
@Service
public class ProductService implements CreateProductUseCase, DeleteProductUseCase,
        GetProductUseCase, UpdateProductUseCase {

    private final ProductRepositoryPort productRepositoryPort;

    /**
     * Constructor para la inyección de dependencias.
     *
     * @param productRepositoryPort Puerto de repositorio para persistencia de productos
     */
    public ProductService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Implementación específica:
     * <ol>
     *   <li>Convierte el DTO de creación a entidad de dominio</li>
     *   <li>Persiste la nueva entidad</li>
     *   <li>Convierte la entidad guardada a DTO de respuesta</li>
     * </ol>
     *
     * @param request DTO con datos para crear el producto
     * @return DTO con la representación del producto creado
     * @throws IllegalArgumentException si el request es nulo o inválido
     */
    @Override
    public ProductResponse createProduct(CreateProductRequest request) {
        Product product = ProductApplicationMapper.toDomainProduct(request);
        Product savedProduct = productRepositoryPort.save(product);
        return ProductApplicationMapper.toProductResponse(savedProduct);
    }

    /**
     * {@inheritDoc}
     *
     * @param id Identificador del producto a eliminar
     * @return true si el producto existía y fue eliminado, false en caso contrario
     * @throws IllegalArgumentException si el id es nulo
     */
    @Override
    public boolean deleteProduct(Long id) {
        return productRepositoryPort.deleteById(id);
    }

    /**
     * {@inheritDoc}
     *
     * @param id Identificador del producto a buscar
     * @return Optional con el producto si existe, vacío si no se encuentra
     * @throws IllegalArgumentException si el id es nulo
     */
    @Override
    public Optional<ProductResponse> getProductById(Long id) {
        Optional<Product> optionalProduct = productRepositoryPort.findById(id);
        return ProductApplicationMapper.toOptionalProductResponse(optionalProduct);
    }

    /**
     * {@inheritDoc}
     *
     * @return Lista de todos los productos disponibles (lista vacía si no hay productos)
     */
    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> productList = productRepositoryPort.findAll();
        return ProductApplicationMapper.toProductResponseList(productList);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Implementación específica:
     * <ol>
     *   <li>Busca el producto existente</li>
     *   <li>Aplica las actualizaciones</li>
     *   <li>Persiste los cambios</li>
     *   <li>Retorna la representación actualizada</li>
     * </ol>
     *
     * @param id Identificador del producto a actualizar
     * @param request DTO con los datos de actualización
     * @return DTO con la representación del producto actualizado
     * @throws IllegalArgumentException si el id o request son nulos/inválidos
     * @throws RuntimeException si no se encuentra el producto
     */
    @Override
    public ProductResponse updateProduct(Long id, UpdateProductRequest request) {
        Product existingProduct = productRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        ProductApplicationMapper.updateDomainProduct(existingProduct, request);
        Product updatedProduct = productRepositoryPort.save(existingProduct);
        return ProductApplicationMapper.toProductResponse(updatedProduct);
    }
}