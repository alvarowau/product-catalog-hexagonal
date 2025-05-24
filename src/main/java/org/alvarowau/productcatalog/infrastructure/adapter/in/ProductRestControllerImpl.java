package org.alvarowau.productcatalog.infrastructure.adapter.in;

import org.alvarowau.productcatalog.application.port.in.*;
import org.alvarowau.productcatalog.infrastructure.adapter.in.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del controlador REST para operaciones de productos.
 * <p>
 * Esta clase maneja las peticiones HTTP y delega la lógica de negocio
 * a los casos de uso correspondientes.
 */
@RestController
@RequestMapping("/product")
public class ProductRestControllerImpl implements ProductRestController {

    private final CreateProductUseCase createProductUseCase;
    private final GetProductUseCase getProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;

    /**
     * Constructor para inyección de dependencias.
     *
     * @param createProductUseCase Caso de uso para creación de productos
     * @param getProductUseCase Caso de uso para obtención de productos
     * @param updateProductUseCase Caso de uso para actualización de productos
     * @param deleteProductUseCase Caso de uso para eliminación de productos
     */
    public ProductRestControllerImpl(CreateProductUseCase createProductUseCase,
                                     GetProductUseCase getProductUseCase,
                                     UpdateProductUseCase updateProductUseCase,
                                     DeleteProductUseCase deleteProductUseCase) {
        this.createProductUseCase = createProductUseCase;
        this.getProductUseCase = getProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Maneja peticiones POST para crear nuevos productos.
     *
     * @param request Datos del producto a crear
     * @return ResponseEntity con el producto creado (201) o error (400)
     */
    @Override
    public ResponseEntity<ProductResponse> createProduct(CreateProductRequest request) {
        ProductResponse response = createProductUseCase.createProduct(request);
        if (response == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Maneja peticiones GET para obtener un producto específico.
     *
     * @param id ID del producto a buscar
     * @return ResponseEntity con el producto (200) o no encontrado (404)
     */
    @Override
    public ResponseEntity<ProductResponse> getProductById(Long id) {
        Optional<ProductResponse> response = getProductUseCase.getProductById(id);
        return response.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Maneja peticiones GET para listar todos los productos.
     *
     * @return ResponseEntity con lista de productos (200) o sin contenido (204)
     */
    @Override
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> responses = getProductUseCase.getAllProducts();
        if (responses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responses);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Maneja peticiones PUT para actualizar productos existentes.
     *
     * @param id ID del producto a actualizar
     * @param request Datos de actualización
     * @return ResponseEntity con producto actualizado (200) o no encontrado (404)
     */
    @Override
    public ResponseEntity<ProductResponse> updateProduct(Long id, UpdateProductRequest request) {
        try {
            ProductResponse response = updateProductUseCase.updateProduct(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Maneja peticiones DELETE para eliminar productos.
     *
     * @param id ID del producto a eliminar
     * @return ResponseEntity sin contenido (204) o no encontrado (404)
     */
    @Override
    public ResponseEntity<Void> deleteProduct(Long id) {
        boolean response = deleteProductUseCase.deleteProduct(id);
        return response ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}